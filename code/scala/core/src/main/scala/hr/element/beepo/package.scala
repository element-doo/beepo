package hr.element

package object beepo {
  implicit class PimpedURLString(url: String) {
    def /(path: String): String = "%s/%s" format(url, path)
  }
}