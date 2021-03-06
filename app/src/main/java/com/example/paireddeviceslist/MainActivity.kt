package com.example.paireddeviceslist

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var bluetoothPairedDevices : Set<BluetoothDevice>
    lateinit var btnPairedList:Button
    lateinit var pairedListView: ListView
    lateinit var bluetoothAdapter: BluetoothAdapter

    lateinit var btnOn:Button
    lateinit var btnOff:Button
    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPairedList = findViewById(R.id.pairedListViewButton)
        pairedListView = findViewById(R.id.pairedListView)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        btnOn = findViewById(R.id.onButton)
        btnOff = findViewById(R.id.offButton)

        if (bluetoothAdapter == null)
        {
            btnOn.isEnabled = false
            btnOff.isEnabled = false

            Toast.makeText(applicationContext,"Your Device Does not Support Bluetooth..",Toast.LENGTH_LONG).show()
        }
        else
        {
            btnOn.setOnClickListener {
                onBluetooth()
            }
        }

        btnOff.setOnClickListener {
            offBluetooth()
        }

        btnPairedList.setOnClickListener {
            if (bluetoothAdapter.isEnabled){
                getPairedDevicesList()
            }
            else
            {
                Toast.makeText(applicationContext,"First turned on bluetooth....",Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun getPairedDevicesList(){
        bluetoothPairedDevices = bluetoothAdapter.bondedDevices
        val listView = ArrayList<String>()
        for (bluetoothDevices:BluetoothDevice in bluetoothPairedDevices)
        {
            listView.add(bluetoothDevices.name +"\n" + bluetoothDevices.address)
        }
        Toast.makeText(applicationContext,"Showing Paired Devices...",Toast.LENGTH_LONG).show()
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,listView)
        pairedListView.adapter = adapter
    }

    private fun offBluetooth(){
        bluetoothAdapter.disable()
        Toast.makeText(applicationContext,"Bluetooth Turned off ...",Toast.LENGTH_LONG).show()
    }

    private fun onBluetooth(){
        if (!bluetoothAdapter.isEnabled){
            val turnOn = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(turnOn,REQUEST_CODE)
            Toast.makeText(applicationContext,"Bluetooth Turned on...",Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(applicationContext,"Bluetooth is already on...",Toast.LENGTH_LONG).show()
        }
    }
}