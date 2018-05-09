package de.basedow.keno.starfishcollector

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion

open class AnimatedActor : BaseActor() {

    private var elapsedTime = 0f
    private var activeAnim: Animation<TextureRegion>? = null
    var activeName: String? = null
        private set
    private var animationStorage = mutableMapOf<String, Animation<TextureRegion>>()

    fun storeAnimation(name: String, anim: Animation<TextureRegion>) {
        animationStorage.put(name, anim)
        if (activeName == null)
            setActiveAnimation(name)
    }

    fun setActiveAnimation(name: String) {
        if (!animationStorage.containsKey(name))
            return

        if (activeName == name)
            return

        activeName = name
        activeAnim = animationStorage.get(name)
        elapsedTime = 0f

        val tex = activeAnim?.getKeyFrame(0f)?.texture
        width = tex?.width?.toFloat() ?: 0f
        height = tex?.height?.toFloat()?: 0f
    }

    override fun act(delta: Float) {
        super.act(delta)
        elapsedTime += delta
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        region.setRegion(activeAnim?.getKeyFrame(elapsedTime))
        super.draw(batch, parentAlpha)
    }
}
