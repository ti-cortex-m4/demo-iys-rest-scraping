package demo.model

import com.google.api.client.util.Key
import com.google.common.collect.Lists
import java.util.Collections

class TargetsList {

    @Key("size")
    var size: Int = 0

    @Key("targets")
    var targets: MutableList<Target> = Lists.newArrayList()

    fun updateTargets(targets: Collection<Target>) {
        this.targets.clear()
        this.targets.addAll(targets)
        Collections.sort<Target>(this.targets)
        this.size = targets.size
    }
}
