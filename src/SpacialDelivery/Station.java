package SpacialDelivery;

class Station {

    private String name;
    private PostOffice postOffice;

    Station(String name) {
        this.name = name;
        this.postOffice = new PostOffice();
        postOffice.setStation(this);
    }

    String getName() {
        return name;
    }

    PostOffice getPostOffice(){
        return postOffice;
    }
}
