package de.basedow.keno.starfishcollector

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class TurtleMenu(game: BaseGame) : BaseScreen(game) {

    init {
        val waterTex = Texture("water.jpg")
        waterTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        game.skin.add("waterTex", waterTex)
        uiTable.background(game.skin.getDrawable("waterTex"))

        val titleTex = Texture("starfish-collector.png")
        titleTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        val titleImage = Image(titleTex)

        val libgdxTex = Texture("created-libgdx.png")
        libgdxTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        val libgdxImage = Image(libgdxTex)

        val startButton = TextButton("Start", game.skin, "uiTextButtonStyle")
        startButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) = true
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                game.screen = TurtleLevel(game)
            }
        })

        val quitButton = TextButton("Quit", game.skin, "uiTextButtonStyle")
        quitButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) = true
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                Gdx.app.exit()
            }
        })

        uiTable.add(titleImage).colspan(2)
        uiTable.row()
        uiTable.add(startButton)
        uiTable.add(quitButton).width(startButton.width)
        uiTable.row()
        uiTable.add(libgdxImage).colspan(2).right().padTop(50f)
    }

    override fun update(delta: Float) {

    }
}