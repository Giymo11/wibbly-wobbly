package models

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.{ProvenShape, Tag}


/**
 * User: Michael Reitgruber
 * Date: 01.07.14
 * Time: 16:11
 */

case class Node(id: Int, parentId: Int, nodeType: String, title: String, desc: String)

class Nodes(tag: Tag) extends Table[Node](tag, "nodes") {

  implicit val intListMapper = MappedColumnType.base[List[Int],String](
    list => list.mkString(","),
    string => string.split(',').toList.map(_.asInstanceOf[Int])
  )
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def parentId = column[Int]("parent_id")
  def nodeType = column[String]("type")
  def title = column[String]("title")
  def desc = column[String]("desc")

  override def * = (id, parentId, nodeType, title, desc) <> (Node.tupled, Node.unapply)
}