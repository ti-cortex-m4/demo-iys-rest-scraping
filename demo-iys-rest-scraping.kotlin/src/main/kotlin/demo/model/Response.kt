package demo.model

import com.google.api.client.util.Key

class Response {

    @Key("status")
    var status: String? = null

    @Key("total")
    var total: Int = 0

    @Key("skills")
    var skills: String? = null
}
