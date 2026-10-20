/**
 * Manages work through the {@link IWorker} abstraction.
 *
 * <p>The caller supplies the worker, so managing work does not depend on a
 * particular worker implementation.
 */
public class Manager {

  private IWorker worker;

  /**
   * Sets the worker that this manager manages.
   *
   * @param worker the worker to manage
   */
  public void setWorker(IWorker worker) {
    this.worker = worker;
  }

  /**
   * Manages the current worker by telling them to do their work.
   *
   * @return a description of the work that was done
   */
  public String manage() {
    return worker.work();
  }
}
