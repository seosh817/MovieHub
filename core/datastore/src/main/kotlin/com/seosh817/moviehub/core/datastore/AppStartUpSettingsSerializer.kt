package com.seosh817.moviehub.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import javax.inject.Inject

class AppStartUpSettingsSerializer @Inject constructor() : Serializer<AppStartUpSettings> {
    override val defaultValue: AppStartUpSettings = AppStartUpSettings.getDefaultInstance()

    override suspend fun readFrom(input: java.io.InputStream): AppStartUpSettings {
        return try {
            AppStartUpSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: AppStartUpSettings, output: java.io.OutputStream) {
        t.writeTo(output)
    }
}