package com.aptiv.settings.permissions

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

/**
 * Activity/Fragment权限申请扩展函数
 */
private inline fun ComponentActivity.requestPermission(
    permission: String,
    crossinline granted: (permission: String) -> Unit = {},
    crossinline denied: (permission: String) -> Unit = {}
) {
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        when {
            result -> granted.invoke(permission)
            else -> denied.invoke(permission)
        }
    }.launch(permission)
}

private inline fun ComponentActivity.requestMultiplePermissions(
    permissions: Array<String>,
    crossinline allGranted: () -> Unit = {},
    crossinline denied: (List<String>) -> Unit = {}
) {
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        val deniedList = it.filter { !it.value }.map { it.key }
        when {
            deniedList.isNotEmpty() -> {
                denied.invoke(deniedList)
            }

            else -> {
                allGranted.invoke()
            }
        }
    }.launch(permissions)
}

/**
 * 申请单个权限
 */
fun ComponentActivity.permission(
    permission: String,
    callback: PermissionBuilder.() -> Unit
) {
    val builder = PermissionBuilder()
    builder.apply(callback)
    requestPermission(
        permission = permission,
        granted = builder.granted,
        denied = builder.denied
    )
}

/**
 * 申请多个权限
 */
fun ComponentActivity.permissions(
    permissions: Array<String>,
    callback: MultiplePermissionsBuilder.() -> Unit
) {
    val builder = MultiplePermissionsBuilder()
    builder.apply(callback)
    requestMultiplePermissions(
        permissions = permissions,
        allGranted = builder.allGranted,
        denied = builder.denied
    )
}