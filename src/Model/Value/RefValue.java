package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type type){
        this.address = address;
        this.locationType = type;
    }

    public int getAddress() {
        return address;
    }

    public Type getType(){
        return new RefType(locationType);
    }

    public Type getLocationType(){
        return locationType;
    }

    public void updateAddress(int add){
        address = add;
    }

    public Value deepCopy(){
        return new RefValue(address, locationType);
    }

    public String toString()
    {
        return address + ", " + locationType.toString();
    }
}
