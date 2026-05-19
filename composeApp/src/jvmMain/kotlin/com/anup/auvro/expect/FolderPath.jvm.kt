package com.anup.auvro.expect

actual fun getDownloadFolderPath(): String = System.getProperty("user.home") + "/Downloads"