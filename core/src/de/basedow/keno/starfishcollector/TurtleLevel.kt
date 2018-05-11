package de.basedow.keno.starfishcollector

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Align

class TurtleLevel(game: Game) : BaseScreen(game) {

    private val ocean = BaseActor()
    private val rockList = mutableListOf<BaseActor>()
    private val starfishList = mutableListOf<BaseActor>()
    private val turtle = PhysicsActor()
    private val mapWidth = 800
    private val mapHeight = 600

    init {
        ocean.texture = Texture("water.jpg")
        ocean.setPosition(0f, 0f)
        mainStage.addActor(ocean)

        val overlay = ocean.clone()
        overlay.setPosition(-50f, -50f)
        overlay.setColor(1f, 1f, 1f, 0.25f)
        uiStage.addActor(overlay)

        val rock = BaseActor()
        rock.texture = Texture("rock.png")
        rock.setEllipseBoundary()

        val rockCoords = arrayOf(200f, 0f, 200f, 100f, 250f, 200f, 360f, 200f, 470f, 200f)
        for (i in 0 until rockCoords.size / 2) {
            val r = rock.clone()
            r.setPosition(rockCoords[i * 2], rockCoords[i * 2 + 1])
            mainStage.addActor(r)
            rockList.add(r)
        }

        val starfish = BaseActor()
        starfish.texture = Texture("starfish.png")
        starfish.setEllipseBoundary()

        val starfishCoords = arrayOf(400f, 100f, 100f, 400f, 650f, 400f)
        for (i in 0 until starfishCoords.size / 2) {
            val s = starfish.clone()
            s.setPosition(starfishCoords[i * 2], starfishCoords[i * 2 + 1])
            mainStage.addActor(s)
            starfishList.add(s)
        }

        val frames = Array<TextureRegion>(6, { i ->
            val tex = Texture("turtle-${i + 1}.png")
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
            TextureRegion(tex)
        })
        val anim = Animation(0.1f, com.badlogic.gdx.utils.Array(frames), Animation.PlayMode.LOOP)
        turtle.storeAnimation("swim", anim)

        turtle.storeAnimation("rest", Texture("turtle-1.png"))

        turtle.setOrigin(Align.center)
        turtle.setPosition(20f, 20f)
        turtle.rotation = 90f
        turtle.setEllipseBoundary()
        turtle.maxSpeed = 100f
        turtle.deceleration = 200f
        mainStage.addActor(turtle)
    }

    override fun update(delta: Float) {

        turtle.setAccelerationXY(0f, 0f)

        when {
            Gdx.input.isKeyPressed(Input.Keys.LEFT) -> turtle.rotateBy(90f * delta)
            Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> turtle.rotateBy(-90f * delta)
            Gdx.input.isKeyPressed(Input.Keys.UP) -> turtle.accelerationForward(100f)
        }

        when {
            turtle.speed > 0 -> turtle.setActiveAnimation("swim")
            else -> turtle.setActiveAnimation("rest")
        }

        turtle.x = MathUtils.clamp(turtle.x, 0f, mapWidth - turtle.width)
        turtle.y = MathUtils.clamp(turtle.y, 0f, mapHeight - turtle.height)

        for (rock in rockList) {
            turtle.overlaps(rock, true)
        }

        val iterator = starfishList.listIterator()
        while (iterator.hasNext()) {
            val starfish = iterator.next()
            if (turtle.overlaps(starfish, false)) {
                iterator.remove()
                starfish.remove()
            }
        }
    }
}