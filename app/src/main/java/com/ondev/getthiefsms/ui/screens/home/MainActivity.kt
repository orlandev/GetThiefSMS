package com.ondev.getthiefsms.ui.screens.home

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ondev.getthiefsms.R
import com.ondev.getthiefsms.data.entity.PhoneNumberData
import com.ondev.getthiefsms.ui.theme.GetThiefSMSTheme
import com.ondev.getthiefsms.utils.IDate
import com.ondev.getthiefsms.utils.phoneList2String
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), EasyPermissions.PermissionCallbacks {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasPermission()) {
            requestAllPermission()
        }
        setContent {
            GetThiefSMSTheme {
                // A surface container using the 'background' color from the theme
                Surface() {
                    App()
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun sharePhoneNumbers() {
        if (!hasPermission()) {
            requestAllPermission()
        } else {

        }
    }

    companion object {
        const val PERMISSION_CODE = 2346
    }


    private fun hasPermission() =
        EasyPermissions.hasPermissions(
            this,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    private fun requestAllPermission() {
        EasyPermissions.requestPermissions(
            this,
            "Se necesitan estos permisos.",
            PERMISSION_CODE,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
        } else {
            requestAllPermission()
        }

    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(
            this,
            resources.getString(R.string.user_permission_garanteed),
            Toast.LENGTH_LONG
        )
            .show()
    }


    @ExperimentalMaterial3Api
    @Composable
    fun App(viewModel: HomeViewModel = hiltViewModel()) {

        val phones = viewModel.allPhone.collectAsState(initial = null)
        val ctx = LocalContext.current

        Scaffold(
            topBar = {
                SmallTopAppBar(
                    title = { Text(text = "PhoneNumbers") },
                    /*actions = {
                        IconButton(onClick = {
                            viewModel.onSimulateSms()
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    }*/
                )
            },

            floatingActionButton = {
                FloatingActionButton(onClick = {
                    phones.value?.let { listPhone ->
                        if (listPhone.isNotEmpty()) {
                            val phoneNumbers =
                                listPhone.map { currentPhone ->
                                    currentPhone.phoneNumber +" - "+ IDate.getDate(
                                        currentPhone.date,
                                        IDate.APP_DATE_PATTER
                                    )
                                }
                                    .phoneList2String()
                            ShareIntent.shareIt(this, phoneNumbers, "Phone Thief App")
                        } else {
                            Toast.makeText(ctx, "Lista Vacia", Toast.LENGTH_LONG).show()
                        }
                    }
                }) {
                    Icon(Icons.Default.Share, contentDescription = "Share")
                }

            }

        ) {
            phones.value?.let {
                PhonesContent(it)
            }
        }
    }

    @Composable
    fun PhonesContent(phones: List<PhoneNumberData>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(phones) { currentItem ->
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    shadowElevation = 1.dp,
                    tonalElevation = 2.dp,
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                        .padding(2.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .weight(2f),
                            overflow = TextOverflow.Ellipsis,
                            text = currentItem.phoneNumber,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .weight(1f),
                            overflow = TextOverflow.Ellipsis,
                            text = IDate.getDate(currentItem.date, IDate.APP_DATE_PATTER),
                            fontSize = 14.sp,
                            textAlign = TextAlign.End
                        )
                    }

                }
            }
        }
    }
}