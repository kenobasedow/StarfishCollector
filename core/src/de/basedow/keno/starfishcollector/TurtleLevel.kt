package de.basedow.keno.starfishcollector

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align

class TurtleLevel(game: BaseGame) : BaseScreen(game) {

    private val ocean = BaseActor()
    private val rockList = mutableListOf<BaseActor>()
    private val starfishList = mutableListOf<BaseActor>()
    private val turtle = PhysicsActor()
    private val mapWidth = 800
    private val mapHeight = 600

    private var audioVolume = 0.80f
    private var waterDrop: Sound
    private var instrument: Music
    private var oceanSurf: Music

    private val starfishLeftLabel: Label

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

        waterDrop = Gdx.audio.newSound(Gdx.files.internal("Water_Drop.ogg"))
        instrument = Gdx.audio.newMusic(Gdx.files.internal("Master_of_the_Feast.ogg"))
        oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("Ocean_Waves.ogg"))

        instrument.isLooping = true
        instrument.volume = audioVolume
        instrument.play()
        oceanSurf.isLooping = true
        oceanSurf.volume = audioVolume
        oceanSurf.play()

        starfishLeftLabel = Label("Starfish Left: --", game.skin, "uiLabelStyle")

        val pauseTexture = Texture("pause.png")
        game.skin.add("pauseImage", pauseTexture)

        val pauseStype = Button.ButtonStyle()
        pauseStype.up = game.skin.getDrawable("pauseImage")

        val pauseButton = Button(pauseStype)
        pauseButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                togglePaused()
                return true
            }
        })

        val uiTable = Table()
        uiTable.setFillParent(true)
        uiTable.pad(10f)
        uiTable.add(starfishLeftLabel)
        uiTable.add().expandX()
        uiTable.add(pauseButton)
        uiTable.row()
        uiTable.add().colspan(3).expandY()
        uiStage.addActor(uiTable)
    }

    override fun update(delta: Float) {

        turtle.setAccelerationXY(0f, 0f)

        when {
            Gdx.input.isKeyPressed(Input.Keys.LEFT) -> turtle.rotateBy(90f * delta)
            Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> turtle.rotateBy(-90f * delta)
            Gdx.input.isKeyPressed(Input.Keys.UP) -> turtle.accelerationForward(100f)
        }

        when {
            turtle.speed > 1 -> turtle.setActiveAnimation("swim")
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
                waterDrop.play(audioVolume)
            }
        }

        starfishLeftLabel.setText("Starfish Left: ${starfishList.size}")
    }

    override fun dispose() {
        waterDrop.dispose()
        instrument.dispose()
        oceanSurf.dispose()
    }
}