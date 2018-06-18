package de.basedow.keno.starfishcollector

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Slider
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class StarfishCollectorGame : BaseGame() {

    override fun create() {

        val uiFont = BitmapFont(Gdx.files.internal("cooper.fnt"))
        uiFont.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        skin.add("uiFont", uiFont)

        val uiLabelStyle = Label.LabelStyle(uiFont, Color.BLUE)
        skin.add("uiLabelStyle", uiLabelStyle)

        val uiTextButtonStyle = TextButton.TextButtonStyle()
        uiTextButtonStyle.font = uiFont
        uiTextButtonStyle.fontColor = Color.NAVY

        val buttonUpTex = Texture("ninepatch-1.png")
        skin.add("buttonUp", NinePatch(buttonUpTex, 26, 26, 16, 20))
        uiTextButtonStyle.up = skin.getDrawable("buttonUp")

        val buttonOverTex = Texture("ninepatch-2.png")
        skin.add("buttonOver", NinePatch(buttonOverTex, 26, 26, 16, 20))
        uiTextButtonStyle.over = skin.getDrawable("buttonOver")

        val buttonDownTex = Texture("ninepatch-3.png")
        skin.add("buttonDown", NinePatch(buttonDownTex, 26, 26, 16, 20))
        uiTextButtonStyle.down = skin.getDrawable("buttonDown")

        skin.add("uiTextButtonStyle", uiTextButtonStyle)

        val uiSliderStyle = Slider.SliderStyle()
        skin.add("sliderBack", Texture("slider-after.png"))
        uiSliderStyle.background = skin.getDrawable("sliderBack")
        skin.add("sliderKnob", Texture("slider-knob.png"))
        uiSliderStyle.knob = skin.getDrawable("sliderKnob")
        skin.add("sliderAfter", Texture("slider-after.png"))
        uiSliderStyle.knobAfter = skin.getDrawable("sliderAfter")
        skin.add("sliderBefore", Texture("slider-before.png"))
        uiSliderStyle.knobBefore = skin.getDrawable("sliderBefore")
        skin.add("uiSliderStyle", uiSliderStyle)

        setScreen(TurtleMenu(this))
    }
}
