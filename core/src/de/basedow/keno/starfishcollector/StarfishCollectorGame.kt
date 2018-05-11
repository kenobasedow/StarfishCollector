package de.basedow.keno.starfishcollector

import com.badlogic.gdx.Game

class StarfishCollectorGame : Game() {

    override fun create() {
        setScreen(TurtleLevel(this))
    }
}
