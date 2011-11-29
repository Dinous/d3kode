/**.
* Package regroupant les actions json de l'application
*/
package org.liris.mTrace.actions.json;

/**.
 * POJO permettant de gérer les listes dans les sj:select
 * @author Dino
 *
 */
public class ListValue {
/**.
 *  myKey
 */
  private String myKey;
  /**.
   *  myValue
   */
  private String myValue;

  /**.
   * Constructeur
   * @param key value du sj:select/option
   * @param value texte du sj:select/option
   */
  public ListValue(final String key, final String value) {
    super();
    this.myKey = key;
    this.myValue = value;
  }

  /**.
   * Ascesseur property key
   * @return String key
   */
  public final String getMyKey() {
    return myKey;
  }

  /**.
   * Ascesseur property key
   * @param key String key
   */
  public final void setMyKey(final String key) {
    this.myKey = key;
  }

  /**.
   * Ascesseur property value
   * @return String value
   */
  public final String getMyValue() {
    return myValue;
  }

  /**.
   * Ascesseur property value
   * @param value String value
   */
  public final void setMyValue(final String value) {
    this.myValue = value;
  }
}
