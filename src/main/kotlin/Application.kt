import tornadofx.*
import java.awt.Toolkit
import java.awt.Toolkit.getDefaultToolkit



class Application: App(ApplicationView::class)

class ApplicationView: View("Programming Mystery") {
    private val questionView: QuestionView by inject()
    val io: IOPanel by inject()

    override val root = borderpane()

    init {
        loadQuestion()
        root.left = questionView.root
        root.right = io.root

        val size = Toolkit.getDefaultToolkit().screenSize
        setWindowMinSize(.67 * size.width, size.height)
    }

    fun loadQuestion(){
        questionView.chooseQuestion()
        questionView.loadAnswers()
    }
}