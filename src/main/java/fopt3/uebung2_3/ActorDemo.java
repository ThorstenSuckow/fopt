package fopt3.uebung2_3;

interface Role {
    void play();
}

class Actor {

    Role[] roles;

    public Actor(Role[] roles) {
        setRoles(roles);
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public void action() {
        for (int i = 0; i < roles.length; i++) {
            roles[i].play();
        }
    }

}

public class ActorDemo {


    public static void main(String[] args) {

        Actor actor = new Actor(new Role[]{
            () -> System.out.println("Rolle 1"),
            () -> System.out.println("Rolle 2"),
        });

        actor.action();

        /**
         * Other solution: USe ArrayList, add individual roles
         */
        actor.setRoles(new Role[0]);
        actor.action();
    }

}


