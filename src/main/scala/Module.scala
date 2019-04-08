import wvlet.airframe._

object Module {
  // Constructor Injection
  val design: Design = newDesign
    .bind[UserRepository].toInstance(new UserRepositoryOnMemoryDB)
    .bind[UserService].to[UserServiceImpl]
}
