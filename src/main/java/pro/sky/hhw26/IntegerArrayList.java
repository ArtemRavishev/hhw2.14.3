package pro.sky.hhw26;

import pro.sky.hhw26.exceptions.ElementNotFoundException;
import pro.sky.hhw26.exceptions.InvalidArgumentException;
import pro.sky.hhw26.exceptions.StringListIndexOutOfBoundsException;

import java.util.Arrays;
import java.util.Objects;

public class IntegerArrayList implements IntegerList {

    private Integer[] data;

    private int currentSize;


    public IntegerArrayList(int size) {
        data = new Integer[size];
        currentSize = 0;
    }

    public IntegerArrayList(Integer... args) {
        data = new Integer[args.length];
        System.arraycopy(args, 0, data, 0, args.length);
        currentSize = data.length;
    }

    @Override
    public Integer add(Integer item) {
        if (currentSize == data.length) {
            resize(currentSize + 1);
        }
        data[currentSize] = item;
        currentSize++;
        return item;

    }

    @Override
    public Integer add(int index, Integer item) {
        checkBounds(index);
        if (currentSize >= data.length) {
            grow();
        }
        for (int i = currentSize; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = item;
        currentSize++;
        return item;
    }

    private void grow() {
        Integer[]data = new Integer[(int)(this.data.length*1.5)];
        System.arraycopy(this.data,0,data,0,this.data.length);
        this.data=data;

    }


    @Override
    public Integer set(int index, Integer item) {
        checkBounds(index);
        data[index] = item;
        return item;
    }


    @Override
    public Integer remove(Integer item) {
        int index = indexOf(item);
        if (index == -1) {
            throw new ElementNotFoundException();
        }
        return remove(index);
    }

    @Override
    public Integer remove(int index) {
        checkBounds(index);
        Integer result = data[index];
        for (int i = index + 1; i < currentSize; i++) {
            data[i - 1] = data[i];
        }
        currentSize--;
        return result;
    }

    @Override
    public boolean contains(Integer item) {
        checkItem(item);
        Integer[] copy = toArray();
        quickSort(copy,0,copy.length-1);
        int min = 0;
        int max = copy.length-1;
        while (min <= max){
            int mid=(min+max)/2;
            if (item.equals(copy[mid])){
                return true;
            }
            if (item<copy[mid]){
                max = mid - 1;}
            else {
                min=mid+1;
            }
        }
        return false;
    }

    private void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr,partitionIndex+1,end);
        }
    }

    private int partition(Integer[] arr, int begin, int end) {
        Integer pivot = arr[end];
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr,i,j);
            }
        }
        swap(arr, i + 1, end);
        return  i + 1;
    }

    private void swap(Integer[] arr, int left, int right) {
        Integer temp = arr[left];
        arr[left] = arr[right];
        arr[right]= temp;
    }
    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < currentSize; i++) {
            if (data[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = currentSize - 1; i >= 0; i--) {
            if (data[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        checkBounds(index);
        return data[index];
    }


    @Override
    public boolean equals(IntegerList other) {
        if (other == null) {
            throw new InvalidArgumentException();
        }
        if (currentSize != other.size()) {
            return false;
        }
        for (int i = 0; i < currentSize; i++) {
            if (!Objects.equals(data[i], other.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public void clear() {
        Arrays.fill(data, 0, currentSize, null);
        currentSize = 0;

    }

    @Override
    public Integer[] toArray() {
        Integer[] array = new Integer[currentSize];
        System.arraycopy(data, 0, array, 0, currentSize);
        return array;

    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < currentSize; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(data[i]);
        }
        result.append("]");
        return result.toString();
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= currentSize) {
            throw new StringListIndexOutOfBoundsException();
        }
    }


    private void resize(int newSize) {
        int size = currentSize * 2;
        size = Math.max(size, newSize);
        Integer[] newData = new Integer[size];
        System.arraycopy(data, 0, newData, 0, currentSize);
        data = newData;
    }

    private void checkItem(Integer item) {
        if (item == 0 ) {
            throw new InvalidArgumentException();
        }
    }
}