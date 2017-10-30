package com.zxdz.car.main.view.lock;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.R;
import com.zxdz.car.base.utils.SwitchUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.adapter.ExpandAdapter;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class LockActivity extends BaseActivity {

    @BindView(R.id.lock_toolbar)
    Toolbar lockToolbar;
    @BindView(R.id.lock_explv)
    ExpandableListView lockExplv;
    private BluetoothAdapter mBluetoothAdapter;
    private ExpandAdapter expandAdapter;
    private static final UUID Server_UUID = UUID.fromString("0000fee7-0000-1000-8000-00805f9b34fb");
    private static final UUID Read_UUID = UUID.fromString("000036f6-0000-1000-8000-00805f9b34fb");
    private static final UUID Write_UUID = UUID.fromString("000036f5-0000-1000-8000-00805f9b34fb");
    private byte[] key = {32, 87, 47, 82, 54, 75, 63, 71, 48, 80, 65, 88, 17, 99, 45, 43};//秘钥
    private byte[] bytes_open = {0x05, 0x01, 0x06, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};//开锁
    private byte[] bytes_query = {0x05, 0x0e, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};//查询锁状态
    private byte[] open_Gsenser = {0x05, 0x06, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};//开启Gsenser
    private byte[] close_Gsenser = {0x05, 0x06, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};//关闭Gsenser
    private byte[] open_GPRS = {0x05, 0x10, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};//开启GPRS
    private byte[] close_GPRS = {0x05, 0x10, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};//关闭GPRS
    private String TAG = "LockActivity";
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCharacteristic writer_characteristic;
    private BluetoothGattCharacteristic read_characteristic;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lock;
    }

    @Override
    public void init() {
        basetoobar(lockToolbar, "开关锁");
        //进入开关锁页面默认开启蓝牙自动扫描，客户手动授权
        initdata();
        openScan();
    }

    //加密
    private byte[] encryption(byte[] bytes) {
        byte[] encrypt = SwitchUtils.encrypt(key, bytes);
        String str = SwitchUtils.byte2HexStr(encrypt).replaceAll(" ", "");
        byte[] bytes1 = SwitchUtils.hexStringToByte(str);
        return bytes1;
    }

    //写命令
    private void writecharacteristic(byte[] bytes) {
        if (bluetoothGatt == null || writer_characteristic == null) {
            Toast.makeText(this, "请连接设备", Toast.LENGTH_SHORT).show();
            return;
        }
        writer_characteristic.setValue(bytes);
        boolean statue = bluetoothGatt.writeCharacteristic(writer_characteristic);
        String str = statue == true ? "发送成功" : "发送失败";
        LogUtils.e(TAG, str);
    }


    @OnClick({R.id.toobar_scan, R.id.lock_openLock, R.id.lock_closeLock, R.id.lock_open_alarm, R.id.lock_close_alarm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toobar_scan:
                scanning();
                break;
            case R.id.lock_openLock:
                writecharacteristic(encryption(bytes_open));
                break;
            case R.id.lock_closeLock:
                //锁手动关闭，关闭后锁会有反馈给app（要先连接再关锁），这边可以查询锁是否关闭，
                writecharacteristic(encryption(bytes_query));
                break;
            case R.id.lock_open_alarm:
                writecharacteristic(encryption(open_Gsenser));
                writecharacteristic(encryption(open_GPRS));
                break;
            case R.id.lock_close_alarm:
                writecharacteristic(encryption(close_GPRS));
                writecharacteristic(encryption(close_Gsenser));
                break;
        }
    }


    //初始化ExpandableListView
    private void initdata() {
        expandAdapter = new ExpandAdapter();
        lockExplv.setAdapter(expandAdapter);
        lockExplv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO: 2017/10/26
                //点击链接设备
                BluetoothDevice device = expandAdapter.getDevice(groupPosition, childPosition);
                String address = device.getAddress();
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();//停止扫描蓝牙
                }
                //链接开始
                Toast.makeText(LockActivity.this, "连接开始", Toast.LENGTH_SHORT).show();
                bluetoothGatt = device.connectGatt(LockActivity.this, false, mGattCallback);
                return false;
            }
        });
    }

    //开启蓝牙并扫描
    private void openScan() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //开启蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "当前设备不支持蓝牙功能！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            boolean enable = mBluetoothAdapter.enable(); //返回值表示 是否成功打开了蓝牙设备
            if (enable) {
                Toast.makeText(this, "打开蓝牙功能成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "打开蓝牙功能失败，请到'系统设置'中手动开启蓝牙功能！", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {//针对设备不支持ble，可以进行操作
            finish();
        }
        //注册广播
        regist();
    }

    //注册广播
    private void regist() {
        //蓝牙查询,可以在reciever中接受查询到的蓝牙设备
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(BluetoothDevice.ACTION_FOUND);//搜索发现设备
        mFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);// 注册搜索完时的receiver
        mFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED); //蓝牙连接状态发生改变时,接收状态
        registerReceiver(mReceiver, mFilter);
        scanning();
    }
    //扫描开始
    private void scanning() {
        //通过适配器对象调用isEnabled()方法，判断蓝牙是否打开了
        if (mBluetoothAdapter.isEnabled()) {
            //如果蓝牙已经打开,判断此时蓝牙是否正在扫描,如果正在扫描,则先停止当前扫描,然后在重新扫描
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            Toast.makeText(this, "开始扫描", Toast.LENGTH_SHORT).show();
            mBluetoothAdapter.startDiscovery();
        } else {
            //如果没有开启蓝牙，调用系统方法,让用户确认开启蓝牙
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(enableBtIntent);
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 获得已经搜索到的蓝牙设备
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                Toast.makeText(context, "搜索到蓝牙设备", Toast.LENGTH_SHORT).show();
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);//通过此方法获取搜索到的蓝牙设备
                // 获取搜索到的蓝牙绑定状态,看看是否是已经绑定过的蓝牙
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) { // 如果没有绑定过
                    expandAdapter.addNewDevice(device);
                } else if (device.getBondState() == BluetoothDevice.BOND_BONDED) {// 如果绑定过
                    expandAdapter.addPairedDevices(device);
                }// 搜索完成
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                Toast.makeText(context, "蓝牙扫描结束", Toast.LENGTH_SHORT).show();
            } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                //获取发生改变的蓝牙对象
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //根据不同的状态显示提示
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        Toast.makeText(context, "/配对成功", Toast.LENGTH_SHORT).show();
                        // handler.sendEmptyMessageDelayed(1,2000);
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
                        Log.d("yxs", "取消配对");
                    default:
                        break;
                }
            }
        }
    };

    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);//解除注册
        }
    }

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override//当连接上设备或者失去连接时会回调该函数
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) { //连接成功
                Toast.makeText(LockActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                bluetoothGatt.discoverServices(); //连接成功后就去找出该设备中的服务 private BluetoothGatt mBluetoothGatt;
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {  //连接失败
                Toast.makeText(LockActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override //当设备是否找到服务时，会回调该函数
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //在这里可以对服务进行解析，寻找到你需要的服务
                List<BluetoothGattService> gattServices = getSupportedGattServices();
                for (BluetoothGattService gattService : gattServices) {// 遍历出gattServices里面的所有服务
                    List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                    for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {// 遍历每条服务里的所有Characteristic
                        if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(Write_UUID.toString())) {
                            writer_characteristic = gattCharacteristic;
                        }
                        if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(Read_UUID.toString())) {
                            read_characteristic = gattCharacteristic;
                        }
                    }
                }
            } else {
                LogUtils.e(TAG, "没有服务");
            }
        }

        public void readCharacteristic(BluetoothGattCharacteristic characteristic) {//可读的UUID
            LogUtils.e(TAG, "readCharacteristic");
            if (mBluetoothAdapter == null || bluetoothGatt == null) {
                return;
            }
            bluetoothGatt.readCharacteristic(characteristic);


        }

        public void wirteCharacteristic(BluetoothGattCharacteristic characteristic) {//可写的UUID
            LogUtils.e(TAG, "wirteCharacteristic");
            if (mBluetoothAdapter == null || bluetoothGatt == null) {
                return;
            }
            bluetoothGatt.writeCharacteristic(characteristic);
        }

        //要设置一下可以接收通知Notifaction
        public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
            LogUtils.e(TAG, "setCharacteristicNotification");
            if (mBluetoothAdapter == null || bluetoothGatt == null) {
                return;
            }
            bluetoothGatt.setCharacteristicNotification(characteristic, enabled);
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(Server_UUID);
            if (descriptor != null) {
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                bluetoothGatt.writeDescriptor(descriptor);
            }
        }

        @Override //当读取设备时会回调该函数
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            LogUtils.e(TAG, "onCharacteristicRead");
            BluetoothGattDescriptor dsc = characteristic.getDescriptor(Read_UUID);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //读取到的数据存在characteristic当中，可以通过characteristic.getValue();函数取出。然后再进行解析操作。
                //int charaProp = characteristic.getProperties();if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0)表示可发出通知。  判断该Characteristic属性
            }
        }

        @Override//设备发出通知时会调用到该接口
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            LogUtils.e(TAG, "onCharacteristicChanged");
            //收到通知，开始读
            if (mBluetoothAdapter == null || bluetoothGatt == null) {
                return;
            }
            bluetoothGatt.readCharacteristic(characteristic);
            byte[] value = characteristic.getValue();
            if (value!= null) {
                byte[] decrypt = SwitchUtils.decrypt(key, value);//解密
                //判断命令
                LogUtils.e("收到的字节数组","");
            }
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            LogUtils.e(TAG, "onReadRemoteRssi");
        }

        @Override//当向Characteristic写数据时会回调该函数
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            LogUtils.e(TAG, "onCharacteristicWrite");
            gatt.readCharacteristic(characteristic);
        }
    };

    public List<BluetoothGattService> getSupportedGattServices() {
        if (bluetoothGatt == null)
            return null;
        return bluetoothGatt.getServices();   //此处返回获取到的服务列表
    }
}
