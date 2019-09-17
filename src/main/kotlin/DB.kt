import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object DB {
    private val dbDir = "${System.getProperty("user.home")}/programming-mystery"

    object Names {
        const val CODE_PATH = "code path"
        const val LAST_CLASS = "last class"
    }

    object Properties: Table(){
        val name = text("name").primaryKey()
        val value = text("value")
    }

    object Teams : Table(){
        val id = integer("id").autoIncrement().primaryKey()
        val `class` = text("class")
        val name = text("name")
        val score = integer("score")
    }

    init {
        File(dbDir).mkdir()
        val dbPath = "$dbDir/data.db"
        File(dbPath).createNewFile()
        Database.connect("jdbc:sqlite:$dbPath", "org.sqlite.JDBC")

        transaction {
            SchemaUtils.createMissingTablesAndColumns(Properties, Teams)
            createProperty(Names.CODE_PATH, System.getProperty("user.home"))
            createProperty(Names.LAST_CLASS, "default")
            createDefaultTeams()
        }
    }

    fun getPropertyValue(propertyName: String): String {
        return transaction {
            Properties
                .slice(Properties.value)
                .select { Properties.name eq propertyName}
                .limit(1)
                .map {
                    it[Properties.value]
                }.first()
        }
    }

    fun setPropertyValue(propertyName: String, newValue: String) {
        transaction {
            Properties.update({ Properties.name eq propertyName }) {
                it[value] = newValue
            }
        }
    }

    fun getTeams(`class`: String): List<Team> {
        return transaction {
            Teams
                .slice(Teams.id, Teams.name, Teams.score)
                .select { Teams.`class` eq `class` }
                .map { Team(it[Teams.id], it[Teams.name], it[Teams.score]) }
        }
    }

    fun createTeams(`class`: String, teams: List<String>){
        transaction {
            Teams.batchInsert(teams){ name ->
                this[Teams.`class`] = `class`
                this[Teams.name] = name
                this[Teams.score] = 0
            }
        }
    }

    fun getClasses(): List<String> {
        return transaction {
            Teams
                .slice(Teams.`class`)
                .selectAll()
                .withDistinct()
                .map { it[Teams.`class`] }
        }
    }

    fun updateScore(id: Int, newScore: Int){
        transaction {
            Teams.update({ Teams.id eq id }){
                it[score] = newScore
            }
        }
    }

    private fun createProperty(propertyName: String, propertyValue: String){
        if( Properties.select { Properties.name eq propertyName }.empty() ){
            Properties.insert {
                it[name] = propertyName
                it[value] = propertyValue
            }
        }
    }

    private fun createDefaultTeams(){
        if(Teams.select { Teams.`class` eq "default" }.empty()){
            val teams = listOf("Team 1", "Team 2", "Team 3", "Team 4", "Team 5", "Team 6")
            Teams.batchInsert(teams){ name ->
                this[Teams.`class`] = "default"
                this[Teams.name] = name
                this[Teams.score] = 0
            }
        }
    }
}