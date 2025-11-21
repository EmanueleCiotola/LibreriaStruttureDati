package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;

/** Interface: Sequence con supporto alla rimozione di un dato tramite posizione. */
public interface RemovableAtSequence<Data> extends Sequence<Data> {

  /* ************************************************************************ */
  /* Default removal methods                                                    */
  /* ************************************************************************ */

  default void RemoveAt(Natural position) {
    long index = ExcIfOutOfBound(position);
    AtNRemove(Natural.Of(index));
  }

  Data AtNRemove(Natural position);

  default void RemoveFirst() {
    if (Size().IsZero()) return; //TODO il prof non lo mette, decido quando implemento AtNRemove
    AtNRemove(Natural.ZERO);
  }

  default Data FirstNRemove() {
    if (Size().IsZero()) return null; //TODO il prof non lo mette, decido quando implemento AtNRemove
    return AtNRemove(Natural.ZERO);
  }

  default void RemoveLast() {
    if (Size().IsZero()) return; //? a differenza di SetLast (MutableSequence), in questo caso non ha senso lanciare un'eccezione
    AtNRemove(Size().Decrement()); //TODO diverso, vedere da foto
  }

  default Data LastNRemove() {
    if (Size().IsZero()) return null;
    return AtNRemove(Size().Decrement()); //TODO diverso, vedere da foto
  }

}
