import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object DB {
    private val dbDir = "${System.getProperty("user.home")}/programming-mystery"

    object Properties: Table(){
        val name = text("name").primaryKey()
        val value = text("value")
    }

    object Teams : Table(){
        val id = integer("id").autoIncrement().primaryKey()
        val group = text("group")
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
            createProperty("code path")
        }
    }

    fun getCodePath(): String {
        return transaction {
            Properties
                .slice(Properties.value)
                .select { Properties.name eq "code path" }
                .limit(1)
                .map {
                    it[Properties.value]
                }.first()
        }
    }

    fun setCodePath(newPath: String) {
        transaction {
            Properties.update({ Properties.name eq "code path" }) {
                it[value] = newPath
            }
        }
    }

    private fun createProperty(propertyName: String){
        if( Properties.select { Properties.name eq propertyName }.empty() ){
            Properties.insert {
                it[name] = propertyName
                it[value] = System.getProperty("user.home")
            }
        }
    }
}