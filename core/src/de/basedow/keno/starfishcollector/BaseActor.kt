package de.basedow.keno.starfishcollector

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor

open class BaseActor : Actor() {

    var region = TextureRegion()
    var boundingPolygon: Polygon? = null
        get() {
            field?.setPosition(x, y)
            field?.rotation = rotation
            return field
        }

    var texture: Texture? = null
        set(value) {
            field = value
            region.setRegion(value)
            if (value == null) return;
            width = value.width.toFloat()
            height = value.height.toFloat()
        }

    fun setRectangleBoundary() {
        boundingPolygon = Polygon(floatArrayOf(0f, 0f, width, 0f, width, height, 0f, height))
        boundingPolygon?.setOrigin(originX, originY)
    }

    fun setEllipseBoundary() {
        val n = 8
        val vertices = FloatArray(n * 2)
        for (i in 0 until n) {
            val t = i.toFloat() * 6.28f / n.toFloat()
            vertices[2 * i] = width / 2 * MathUtils.cos(t) + width / 2
            vertices[2 * i + 1] = height / 2 * MathUtils.sin(t) + height / 2

        }
        boundingPolygon = Polygon(vertices)
        boundingPolygon?.setOrigin(originX, originY)
    }

    override fun act(delta: Float) {
        super.act(delta)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch?.setColor(color.r, color.g, color.b, color.a)
        if (isVisible)
            batch?.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

}
