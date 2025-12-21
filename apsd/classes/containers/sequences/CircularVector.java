package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.CircularVectorBase;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete (static) circular vector implementation. */
public class CircularVector<Data> extends CircularVectorBase<Data> {

  public CircularVector() { super(Natural.ZERO); }
  public CircularVector(Natural initialSize) { super(initialSize); }
  public CircularVector(Data[] arr) {
    super(Natural.Of(arr.length));
    for (int i = 0; i < arr.length; i++) { this.arr[i] = arr[i]; }
  }
  public CircularVector(TraversableContainer<Data> container) {
    super(container != null ? Natural.Of(container.Size().ToLong()) : Natural.ZERO);
    if (container != null) {
      Box<Natural> index = new Box<>(Natural.ZERO);
      container.TraverseForward(data -> {
        SetAt(data, index.Get());
        index.Set(index.Get().Increment());
        return false;
      });
    }
  }

  @Override
  protected CircularVector<Data> NewVector(Data[] arr) { return new CircularVector<>(arr); }

}
