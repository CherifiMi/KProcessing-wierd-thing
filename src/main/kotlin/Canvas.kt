import processing.core.PApplet
import processing.event.MouseEvent
import processing.opengl.PShader

fun main() {
    Canvas
}


object Canvas : PApplet() {
    init {
        this.runSketch()
    }

    var cellW = 0f
    var cellH = 0f
    var cellCount = 32
    var camRot = PI * 2 + PI / 6
    var planeWOff = 0f
    var planeHOff = 0f
    var shader: PShader? = null
    var sizeZ = 0f

    override fun mouseWheel(event: MouseEvent) {
        val e = event.getCount()
        cellCount += e
        println(e)
    }

    override fun settings() {
        size(560, 560, P3D)
    }
    override fun setup() {
        System.gc()
        cellW = width / cellCount * 6f
        cellH = height / cellCount * 6f
        planeWOff = max(0f, cellW * cellCount - width)
        planeHOff = max(0f, cellW * cellCount - height)
        shader = loadShader("assets/shader.glsl")
        println(shader)
    }

    override fun draw() {
        background(000000f)
        camRot += .01f
        camera(
            width / 2 + cos(camRot) * 4000,
            height / 2 - sin(camRot) * 4000,
            -1400f,
            width / 2f,
            height / 2f,
            0f,
            0f,
            0f,
            1f
        )
        ortho(-width * 4.55f, width * 4.55f, -height * 4.55f, height * 4.55f)
        blendMode(ADD)
        colorMode(HSB)

        for (yy in 0..cellCount) {
            for (xx in 0..cellCount) {
                pushMatrix()
                val _xx = xx - cellCount / 2
                val _yy = yy - cellCount / 2
                sizeZ =
                    (sin((_xx * .14f) * (_yy * .14f) + camRot * 4f + .25f * cos(_xx * .14f + _yy * .14f + camRot)) * .8f + 1f) * 400f + 100f
                fill((xx + yy + (camRot * (PI / 4)) * 255) % 255, 100f, sizeZ * .025f)
                stroke((xx + yy + (camRot * (PI / 4)) * 255) % 255, 200f, 255f * .2f)
                strokeWeight(sizeZ * .05f)
                translate(
                    xx * cellW + cellW / 2 - planeWOff / 2,
                    yy * cellH + cellH / 2 - planeHOff / 2,
                    -100 - sizeZ / 2 + 500
                )
                box(cellW, cellH, cellW + sizeZ)
                popMatrix()
            }
        }

        blendMode(NORMAL)
        filter(shader)
        shader?.set("time", camRot * 6)
    }
}

