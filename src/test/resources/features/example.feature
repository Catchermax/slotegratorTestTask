Feature: example feature

  @example
  Scenario Outline: example scenario
    Given open page "Login Page"
    And on "Login page" log in with a login "<login>" and password "<password>"
    Then on "Dashboard page" page was loaded
    And on "Dashboard page" in the "Left Panel" open "Users"
    And on "Dashboard page" in the "Left Panel" open "Players"
    Then on "Players page" table has 20 rows
    And on "Players page" sort table by "Username"
    Then on "Players page" table was sorted by "Username"
    Examples:
      | login  | password         |
      | admin1 | [9k<k8^z!+$$GkuP |