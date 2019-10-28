/**
 * Klassen står for all logikk som manipulerer AVLTreet
 */


public class AVLTre {

    /**
     * Node-klassa (Nesta så AVLTre skal få lettere tilgang)
     */
    public class AVLNode {
        public AVLNode venstreNode, hoyreNode; // Referanser til høyre og venstre node
        public int hoyde = 1;                  // Høyde på node
        public int verdi;                      // Verdi på node

        private AVLNode(int verdi) {
            this.verdi = verdi;
        }
    }

    /**
     *
     * @param node referanse til node
     * @param value Ny verdi som skal bli lagt inn
     *
     * @return new AVLNode(): Om ingen node fins på plassen nåværende referanse ligger,
     * lag ny node med ny verdi. (Denne vil bare treffe etter rekursjonen kommer til en ledig plass i treet.)
     * @return rotasjon(): Om noden er ubalansert, vil disse metodene gi noden en ny referanse av en balansert node.
     * @return node: Returnerer den originale noden om den ikke er ubalansert.
     */
    public AVLNode insert(AVLNode node, int value) {
        /* Om node er null, legg inn ny node */
        if (node == null) {
            return(new AVLNode(value));
        }

        /*
        Sjekker om node-verdi er høyere eller mindre, og gjør et rekursivt kall
        til referansen stopper på null som betyr at plassen er ledig.
        */
        if (value < node.verdi)
            node.venstreNode = insert(node.venstreNode, value);
        else
            node.hoyreNode = insert(node.hoyreNode, value);

        /* 2. Oppdater høyden på node */
        node.hoyde = Math.max(height(node.venstreNode), height(node.hoyreNode)) + 1;

        /* 3. Hent balansen til denne noden for å sjekke om den ble ubalansert */
        int balance = getBalance(node);

        /* Om den ble ubalansert, må vi gjennom 4 sjekker */

        // Ubalansert 2 nodes mot venstre
        if (balance > 1 && value < node.venstreNode.verdi){
            return rightRotate(node);
        }

        // Ubalansert 2 nodes mot høyre
        if (balance < -1 && value > node.hoyreNode.verdi)
            return leftRotate(node);

        // Ubalansert mot først venstre så høyre
        if (balance > 1 && value > node.venstreNode.verdi)
        {
            node.venstreNode =  leftRotate(node.venstreNode);
            return rightRotate(node);
        }

        // Ubalansert mot først høyre så venstre
        if (balance < -1 && value < node.hoyreNode.verdi)
        {
            node.hoyreNode = rightRotate(node.hoyreNode);
            return leftRotate(node);
        }

        /* returnerer den uforandra noden */
        return node;
    }

    /**
     *
     * @param y referanse til node
     * @return returnerer node x som erstatter parameter y med en balansert versjon
     */
    private AVLNode rightRotate(AVLNode y) {

        AVLNode x = y.venstreNode;
        AVLNode T2 = x.hoyreNode;

        // Gjør rotasjon
        x.hoyreNode = y;
        y.venstreNode = T2;

        // Oppdater høyder
        y.hoyde = Math.max(height(y.venstreNode), height(y.hoyreNode))+1;
        x.hoyde = Math.max(height(x.venstreNode), height(x.hoyreNode))+1;

        // Returner ny balansert root
        return x;
    }

    /**
     *
     * @param x referanse til node
     * @return returnerer node x som erstatter parameter x med en balansert versjon
     */
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.hoyreNode;
        AVLNode T2 = y.venstreNode;

        // Gjør rotasjon
        y.venstreNode = x;
        x.hoyreNode = T2;

        //  Oppdater høyder
        x.hoyde = Math.max(height(x.venstreNode), height(x.hoyreNode))+1;
        y.hoyde = Math.max(height(y.venstreNode), height(y.hoyreNode))+1;

        // Returner ny balansert root
        return y;
    }

    /**
     * @param N referanse til node
     * @Return returnerer høyden på node
     */
    private int height (AVLNode N) {
        if (N == null)
            return 0;
        return N.hoyde;
    }

    /**
     * @param N referanse til node
     * @return returnerer høyden på node for å sjekke om den er ubalansert
     */
    private int getBalance(AVLNode N) {
        if (N == null)
            return 0;
        return height(N.venstreNode) - height(N.hoyreNode);
    }
}