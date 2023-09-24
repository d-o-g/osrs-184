package jagex.oldscape;

public class ClientParameter {

  public static final ClientParameter A_CLIENT_PARAMETER___1801 = new ClientParameter(7);
  public static final ClientParameter A_CLIENT_PARAMETER___1799 = new ClientParameter();

  static final ClientParameter A_CLIENT_PARAMETER___1803 = new ClientParameter(5);
  static final ClientParameter A_CLIENT_PARAMETER___1798 = new ClientParameter(6);
  static final ClientParameter A_CLIENT_PARAMETER___1797 = new ClientParameter(2);
  static final ClientParameter A_CLIENT_PARAMETER___1795 = new ClientParameter(4);
  static final ClientParameter A_CLIENT_PARAMETER___1793 = new ClientParameter(0);
  static final ClientParameter A_CLIENT_PARAMETER___1794 = new ClientParameter(8);
  static final ClientParameter A_CLIENT_PARAMETER___1802 = new ClientParameter(3);

  public final int id;
  final String name;

  ClientParameter(int id) {
    this.id = id;
    name = "";
  }

  ClientParameter() {
    id = 1;
    name = "";
  }

  public String toString() {
    return name;
  }
}
