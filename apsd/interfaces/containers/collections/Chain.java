package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.RemovableAtSequence;

public interface Chain<Data> extends RemovableAtSequence<Data>, Set<Data> {

  default boolean InsertIfAbsent(Data data) {
    if (!Exists(data)) return Insert(data);
    return false;
  }

  default void RemoveOccurrences(Data data){
    while(Exists(data)){
      Remove(data);
    }
  }

  default Chain<Data> SubChain(Natural from, Natural to) {
    return (Chain<Data>) SubSequence(from, to); //TODO verificare se va bene il cast potenzialmente pericoloso
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  default Natural Search(Data data) { //TODO penso faccia pena. Funziona ma tutti questi array temporanei...
    if (data == null) return null;
    final long[] idx = new long[]{0L};
    final boolean[] found = new boolean[]{false};
    TraverseForward(elem -> {
      if (data.equals(elem)) { found[0] = true; return true; }
      idx[0]++;
      return false;
    });
    return found[0] ? Natural.Of(idx[0]) : null;
  }

}
