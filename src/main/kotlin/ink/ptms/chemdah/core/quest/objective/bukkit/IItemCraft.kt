package ink.ptms.chemdah.core.quest.objective.bukkit

import ink.ptms.chemdah.core.quest.objective.Dependency
import ink.ptms.chemdah.core.quest.objective.ObjectiveCountableI
import org.bukkit.entity.Player
import org.bukkit.event.inventory.CraftItemEvent
import taboolib.platform.util.isNotAir

/**
 * Chemdah
 * ink.ptms.chemdah.core.quest.objective.bukkit.IItemCraft
 *
 * @author sky
 * @since 2021/3/2 5:09 下午
 */
@Dependency("minecraft")
object IItemCraft : ObjectiveCountableI<CraftItemEvent>() {

    override val name = "craft item"
    override val event = CraftItemEvent::class.java

    init {
        handler {
            if (it.inventory.result.isNotAir()) it.whoClicked as Player else null
        }
        addSimpleCondition("position") { data, e ->
            data.toPosition().inside(e.whoClicked.location)
        }
        addSimpleCondition("item") { data, e ->
            data.toInferItem().isItem(e.inventory.result!!)
        }
        addSimpleCondition("item:matrix") { data, e ->
            data.toInferItem().run { e.inventory.matrix.any { item -> isItem(item) } }
        }
    }
}