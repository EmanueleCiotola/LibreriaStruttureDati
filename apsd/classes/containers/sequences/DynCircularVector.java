package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynCircularVectorBase;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete dynamic circular vector implementation. */
public class DynCircularVector<Data> extends DynCircularVectorBase<Data> {

  public DynCircularVector() { super(Natural.ZERO); }
  public DynCircularVector(Natural initialSize) { super(initialSize); }
  public DynCircularVector(Data[] arr) {
    super(Natural.Of(arr.length));
    for (int elem = 0; elem < arr.length; elem++) { this.arr[elem] = arr[elem]; }
    this.size = arr.length;
  }
  public DynCircularVector(TraversableContainer<Data> container) {
    super(container != null ? Natural.Of(container.Size().ToLong()) : Natural.ZERO);
    if (container != null) {
      Box<Natural> index = new Box<>(Natural.ZERO);
      this.size = container.Size().ToLong();
      container.TraverseForward(data -> {
        SetAt(data, index.Get());
        index.Set(index.Get().Increment());
        return false;
      });
    }
  }

  @Override
  protected DynCircularVector<Data> NewVector(Data[] arr) { return new DynCircularVector<>(arr); }

}