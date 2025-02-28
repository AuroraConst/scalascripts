sealed trait GameConcept:
  val conceptdescription:String

enum Agents extends GameConcept:
  val conceptdescription = 
    """agents are the bureaucrats and diplomats that administer your cities and influence powerful interests in the Reach.
    Through play, you can place agents on cards in the Court, a row of cards along the edge of the map (Influence, page 13). 
    If you have the most agents on a card in the Court, you can take it """
  case Beaurocrat,Diplomat 

enum Resources extends GameConcept:
  val conceptdescription = 
    """There are 5 types of resources matching the 5 planet types. Usually, you gain them by taxing cities (page 12) and raiding Rivals in battle (page 16)."""
  case Material,Fuel,Relic,Psionic,Weapon  

enum Planets extends GameConcept:
  val conceptdescription = 
    """Planets are the primary source of resources in the game. Each planet type produces a different resource. 
    You can tax cities on planets to gain resources (page 12) and raid Rivals to steal their resources (page 16)."""
  case Forge,Graveyard,Library,Orbital,Shipyard  


// Define an enumeration for Factions
enum Leaders extends GameConcept:
  val conceptdescription = 
    """Leaders are powerful individuals who can provide players with unique abilities and advantages"""
  case Nobel, OverSeer, Anarchist, //considered S tier
  Elder,Mystic,Archivist,//Considered A Tier
  FeastBringer,QuarterMaster,Upstart,Demagogue, //considered B tier
  FuelDrinker, Sprinter, SignalBreaker,  Warrior,Agitator,Shaper,Corsair, Rebel //consdered C tier


// Define a sealed trait for Clusters
sealed trait Clusters extends GameConcept:
  val conceptdescription = 
    """refers to a group of four connected systems on the board, consisting of three planets and one gate,
      |which are considered a single unit together; essentially, it's a small section of the game board with 
      |interconnected components that are identified by a unique cluster number. 
      |Each cluster contains 3 planets and one gate"""
  val adjacent: Set[Clusters]
// Define case objects for each cluster
case object One extends Clusters:
  val adjacent = Set(Two)

case object Two extends Clusters:
  val adjacent = Set(One, Three)

case object Three extends Clusters:
  val adjacent = Set(Two, Four)

case object Four extends Clusters:
  val adjacent = Set(Three, Five)
case object Five extends Clusters:
  val adjacent = Set(Four, Six)

case object Six extends Clusters:
  val adjacent = Set(Five)


// Define extension methods for Leaders
object LeadersExtensions:
  extension (leader: Leaders)
    def description: String = leader match
      case Leaders.FuelDrinker => "Gives players extra access to fuel"
      case Leaders.Sprinter => "Encourages players to use up excess fuel before it's lost"
      case Leaders.Rebel => "Encourages small strike forces to take on bigger fleets"
      case Leaders.SignalBreaker => "Mitigates the risk of being taken out when using small strike forces"


enum GuildCardSuits extends GameConcept:
  val conceptdescription: String = """FINISH THIS!"""
  case Material,Fuel,Relic,Psionic,Weapon
enum Rules:
  case Rule1,Rule2,Rule3,RuleEtc
enum RaidCost(cost:Int):
  case One extends RaidCost(1)
  case Two extends RaidCost(2)
  case Three extends RaidCost(3)

sealed trait GuildCards:
  val generalDescription = """
  guild cards represent support from powerful groups in the galaxy. They can be used to gain new actions, modifiers, and special abilities
  """
  val suit:GuildCardSuits
  val rules:Set[Rules]
  val raidcost:RaidCost
  

