package com.dlong.retrofitcoroutines.bean

import com.squareup.moshi.*
import java.io.Serializable

/**
 * 默认HTTP请求回复格式1
 *
 * Author: D10NG
 * Time: 11/27/20 10:11 AM
 */
data class BaseResult1(
    var message: String = "",
    var status: String = "",
    var result: Any? = ""
): Serializable


/**
 * 自定义转换器
 * Author: D10NG
 * Time: 11/26/20 5:31 PM
 */
class BaseResult1Adapter: JsonAdapter<BaseResult1>() {

    @FromJson
    override fun fromJson(reader: JsonReader): BaseResult1 {
        val result = BaseResult1()
        try {
            reader.beginObject()
            while (reader.hasNext()) {
                when(reader.nextName()) {
                    "status" -> result.status = reader.nextString()
                    "message" -> result.message = reader.nextString()
                    "result" -> {
                        result.result = reader.readJsonValue()
                    }
                    else -> reader.skipValue()
                }
            }
            reader.endObject()
        } catch (e: Exception){}
        return result
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: BaseResult1?) {
    }

}