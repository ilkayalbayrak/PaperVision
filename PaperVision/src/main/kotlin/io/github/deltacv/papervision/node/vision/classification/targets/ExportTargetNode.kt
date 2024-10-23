/*
 * PaperVision
 * Copyright (C) 2024 Sebastian Erives, deltacv

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.deltacv.papervision.node.vision.classification.targets

import io.github.deltacv.papervision.attribute.misc.StringAttribute
import io.github.deltacv.papervision.attribute.vision.structs.RectAttribute
import io.github.deltacv.papervision.codegen.CodeGen
import io.github.deltacv.papervision.codegen.NoSession
import io.github.deltacv.papervision.codegen.dsl.generators
import io.github.deltacv.papervision.codegen.dsl.targets
import io.github.deltacv.papervision.codegen.language.jvm.JavaLanguage
import io.github.deltacv.papervision.node.Category
import io.github.deltacv.papervision.node.DrawNode
import io.github.deltacv.papervision.node.PaperNode


@PaperNode(
    name = "nod_exporttarget",
    category = Category.CLASSIFICATION,
    description = "des_exporttarget"
)
class ExportTargetNode : DrawNode<NoSession>() {

    val inputTarget = RectAttribute(INPUT, "$[att_targets]")
    val label = StringAttribute(INPUT, "$[att_label]")

    override fun onEnable() {
        + inputTarget
        + label
    }

    override val generators = generators {
        generatorFor(JavaLanguage) {
            current.targets {
                current.scope {
                    addTarget(string(label.value(current).value), inputTarget.value(current).value)
                }
            }

            NoSession
        }
    }

}