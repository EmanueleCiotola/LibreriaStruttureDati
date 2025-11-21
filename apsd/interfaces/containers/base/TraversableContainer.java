package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Accumulator;
import apsd.interfaces.traits.Predicate;

/** Interface: MembershipContainer con supporto all'attraversamento. */
public interface TraversableContainer<Data> extends MembershipContainer<Data> {

  boolean TraverseForward(Predicate<Data> predicate);

  boolean TraverseBackward(Predicate<Data> predicate);

  default <Acc> Acc FoldForward(Accumulator<Data, Acc> fun, Acc initialValue) {
    final Box<Acc> runningResult = new Box<>(initialValue);
    if (fun != null) TraverseForward(current -> { runningResult.Set(fun.Apply(current, runningResult.Get())); return false; });
    return runningResult.Get();
  }

  default <Acc> Acc FoldBackward(Accumulator<Data, Acc> fun, Acc initialValue) {
    final Box<Acc> runningResult = new Box<>(initialValue);
    if (fun != null) TraverseBackward(current -> { runningResult.Set(fun.Apply(current, runningResult.Get())); return false; });
    return runningResult.Get();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */
  
  @Override
  default Natural Size() {
    final MutableNatural sizeCounter = FoldForward((_, runningResult) -> {
      runningResult.Increment();
      return runningResult;
    }, new MutableNatural(0));
    return sizeCounter.ToNatural();
  }

  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */
  
  @Override
  default boolean Exists(Data data) {
    return TraverseForward(current -> (current == null && data == null) || (current.equals(data) && current != null && data != null));
  }

}
