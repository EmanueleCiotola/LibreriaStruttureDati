// package apsd.classes.containers.collections.concretecollections;

// import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
// import apsd.classes.utilities.Natural;
// import apsd.interfaces.containers.base.TraversableContainer;
// import apsd.interfaces.containers.collections.SortedChain;
// import apsd.interfaces.containers.sequences.DynVector;

// /** Object: Concrete set implementation via (dynamic circular) vector. */
// public class VSortedChain<Data extends Comparable<? super Data>> extends VChainBase<Data> implements SortedChain<Data> {

//   public VSortedChain() { super(); }
//   public VSortedChain(TraversableContainer<Data> con) { super(con); }
//   public VSortedChain(VSortedChain<Data> chn) { super(chn.vec); }
//   protected VSortedChain(DynVector<Data> vec) { super(vec); }

//   @Override
//   protected VChainBase<Data> NewChain(DynVector<Data> vec) { return new VSortedChain<>(vec); }

//   /* ************************************************************************ */
//   /* Override specific member functions from InsertableContainer              */
//   /* ************************************************************************ */

//   @Override
//   public boolean Insert(Data data) {
//     if (data == null) return false;
//     //TODO Da fare
//     return true;
//   }

//   /* ************************************************************************ */
//   /* Override specific member functions from Chain                            */
//   /* ************************************************************************ */

//   @Override
//   public boolean InsertIfAbsent(Data data) {
//     if (data == null) return false;
//     //TODO Da fare
//     return true;
//   }
//   @Override
//   public void RemoveOccurrences(Data data) {
//     if (data == null) return;
//     //TODO Da fare
//   }

// }
