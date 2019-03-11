import slick.jdbc.H2Profile.api._


object UserRecordInitializeObject {
  // Definition of User table
  class Users(tag: Tag) extends Table[User](tag,  "USER") {
    def id = column[Int]("ID")
    def name = column[String]("NAME")
    def * = (id, name).mapTo[User]

  }

  // Query Initialize
  val users = TableQuery[Users]

  // create table
  val initializedF = DBIO.seq(
    users.schema.create,
    users += User(1, "t_horikoshi")
  )
}
