package com.seosh817.moviehub.core.data.repository

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.seosh817.moviehub.core.domain.repository.AppVersionsRepository
import com.seosh817.moviehub.core.model.AppVersions
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppVersionsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppVersionsRepository {

    override fun getAppVersion(): AppVersions {
        val packageManager = context.packageManager
        val packageName = context.packageName
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            packageManager.getPackageInfo(packageName, 0)
        }

        return AppVersions(
            versionName = packageInfo.versionName,
            versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
            }
        )
    }
}
