module Security
{
  root Role(Name)
  {
    String Name;
  }

  root InheritedRole(Name, ParentName)
  {
    String Name;
    String ParentName;
    Role(Name) *Role;
    Role(ParentName) *ParentRole;
  }

  root User(name)
  {
    String name;
    String password;
  }
}
