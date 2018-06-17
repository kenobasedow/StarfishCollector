package de.basedow.keno.starfishcollector.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import de.basedow.keno.starfishcollector.StarfishCollectorGame

fun main(args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.width = 800
    config.height = 600
    config.title = "Starfish Collector"
    LwjglApplication(StarfishCollectorGame(), config)
}
