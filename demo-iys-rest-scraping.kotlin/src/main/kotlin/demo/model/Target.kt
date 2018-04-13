package demo.model

import com.google.api.client.util.Key

class Target(

    @Key("id")
    val id: String,

    @Key("name")
    val name: String) : Comparable<Target> {

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other?.javaClass != javaClass)
            return false

        other as Target
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun compareTo(other: Target): Int {
        return this.name.compareTo(other.name)
    }
}
