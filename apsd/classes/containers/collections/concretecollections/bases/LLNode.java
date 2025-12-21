package apsd.classes.containers.collections.concretecollections.bases;

import apsd.classes.utilities.Box;
import apsd.interfaces.traits.MutableReference;
import java.util.Objects;

/** Object: Represents a mutable linked-list node for a value of type Data. */
public class LLNode<Data> implements MutableReference<Data> {

  protected Data data = null;
  protected Box<LLNode<Data>> next = new Box<>();

  public LLNode() {}
  public LLNode(Data data) { this.data = data; }
  public LLNode(Data data, LLNode<Data> nextnode) {
    this(data);
    next.Set(nextnode);
  }
  public LLNode(LLNode<Data> node) {
    if (node == null) throw new NullPointerException("LLNode cannot be null!");
    data = node.data;
    next.Set(new LLNode<>(node.next.Get()));
  }

  /* ************************************************************************ */
  /* Specific member functions of LLNode                                      */
  /* ************************************************************************ */

  public Box<LLNode<Data>> GetNext() { return next; }

  public void SetNext(LLNode<Data> nextnode) { next.Set(nextnode); }

  /* ************************************************************************ */
  /* Override specific member functions from Reference                        */
  /* ************************************************************************ */

  @Override
  public Data Get() { return data; }

  /* ************************************************************************ */
  /* Override specific member functions from MutableReference                 */
  /* ************************************************************************ */

  @Override
  public void Set(Data data) { this.data = data; }

  /* ************************************************************************ */
  /* Override specific member functions from Object                           */
  /* ************************************************************************ */

  @Override
  public int hashCode() {
    return Objects.hash(data, next.Get());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof LLNode<?> node)) return false;
    return (next.Get() == node.next.Get() && Objects.equals(data, node.data));
  }

  @Override
  public String toString() {
    return "LLNode(data: " + data + "; next: " + next + " )";
  }

}
