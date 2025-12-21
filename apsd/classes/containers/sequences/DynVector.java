package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynLinearVectorBase;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Concrete dynamic (linear) vector implementation. */
public class DynVector<Data> extends DynLinearVectorBase<Data> {

  public DynVector() { super(Natural.ZERO); }
  public DynVector(Natural initialSize) { super(initialSize); }
  public DynVector(Data[] arr) {
    super(Natural.Of(arr.length));
    for (int elem = 0; elem < arr.length; elem++) { this.arr[elem] = arr[elem]; }
    this.size = arr.length;
  }
  public DynVector(TraversableContainer<Data> container) {
    super(Natural.Of(container.Size().ToLong()));
    Box<Natural> index = new Box<>(Natural.ZERO);
    this.size = container.Size().ToLong();
    container.TraverseForward(data -> {
      SetAt(data, index.Get());
      index.Set(index.Get().Increment());
      return false;
    });
  }

  @Override
  protected DynVector<Data> NewVector(Data[] arr) { return new DynVector<>(arr); }

}
