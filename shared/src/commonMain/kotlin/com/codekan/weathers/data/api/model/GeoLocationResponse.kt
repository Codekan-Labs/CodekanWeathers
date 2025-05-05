package com.codekan.weathers.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class GeoLocationResponse (

    @SerialName("name"        ) var name       : String?     = null,
    @SerialName("local_names" ) var localNames : LocalNames? = LocalNames(),
    @SerialName("lat"         ) var lat        : Double?     = null,
    @SerialName("lon"         ) var lon        : Double?     = null,
    @SerialName("country"     ) var country    : String?     = null,
    @SerialName("state"       ) var state      : String?     = null,

)

@Serializable
data class LocalNames (

    @SerialName("ar"           ) var ar          : String? = null,
    @SerialName("ascii"        ) var ascii       : String? = null,
    @SerialName("bg"           ) var bg          : String? = null,
    @SerialName("ca"           ) var ca          : String? = null,
    @SerialName("de"           ) var de          : String? = null,
    @SerialName("el"           ) var el          : String? = null,
    @SerialName("en"           ) var en          : String? = null,
    @SerialName("fa"           ) var fa          : String? = null,
    @SerialName("feature_name" ) var featureName : String? = null,
    @SerialName("fi"           ) var fi          : String? = null,
    @SerialName("fr"           ) var fr          : String? = null,
    @SerialName("gl"           ) var gl          : String? = null,
    @SerialName("he"           ) var he          : String? = null,
    @SerialName("hi"           ) var hi          : String? = null,
    @SerialName("id"           ) var id          : String? = null,
    @SerialName("it"           ) var it          : String? = null,
    @SerialName("ja"           ) var ja          : String? = null,
    @SerialName("la"           ) var la          : String? = null,
    @SerialName("lt"           ) var lt          : String? = null,
    @SerialName("pt"           ) var pt          : String? = null,
    @SerialName("ru"           ) var ru          : String? = null,
    @SerialName("sr"           ) var sr          : String? = null,
    @SerialName("th"           ) var th          : String? = null,
    @SerialName("tr"           ) var tr          : String? = null,
    @SerialName("vi"           ) var vi          : String? = null,
    @SerialName("zu"           ) var zu          : String? = null,
    @SerialName("zh"           ) var zh          : String? = null,

)