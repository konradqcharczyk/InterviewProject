import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

public class Changer {

    /**
     * Contains files that need to be checked
     */
    private Vector<File> files;
    /**
     * Contains directions that need to be looked into
     */
    private Vector<File> dirs;
    /**
     * How much files find - [0] and how many changes [1]
     */
    private int[] result;
    
    
    public Changer(){
        dirs = new Vector<File>();
        files = new Vector<File>();
        result = new int[2];
    }
    
    /**
     * Method finds files of given extension in given directory and based on req boolean also in subfolders
     * looks into them to find "find" byte row and changes it to "change" bytes. They need to be in hex.
     * @param startDirectory Start directory
     * @param rxtension What type of files it should look for
     * @param find Byte row to find
     * @param change Byte row to change to
     * @param createCopy If program should create copy or change on existing files
     * @param req If program should looks into subfolders
     * @return
     * @throws IOException 
     */
    public int[] findAndChange(File startDirectory, String extension, String find, String change, boolean createCopy, boolean req) throws IOException {
        result[0] = 0;
        result[1] = 0;
        dirs.clear();
        files.clear();
        findFiles(startDirectory, extension);
        if(req){//looks in subfolders for files
            for(int i = 0; i < dirs.size(); i++){
                findFiles(dirs.get(i), extension);
            }
        }
        checkDirectory();
        for(File f : files)
            try {
                findBytes(f, find, change, createCopy);
            } catch (IOException e) {
                e.printStackTrace();
            }
        files.clear();
        dirs.clear();
        return result;
    }
    
    /**
     * Finds files with given directory and adds them to vector
     * @param start Directory Where should it start
     * @param extension With what extension files should have
     */
    private void findFiles(File startDirectory, String extension){
        File[] matchingFiles = startDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File tmp = new File(dir + "\\" + name);
                if(tmp.isDirectory()){
                    dirs.add(tmp);
                }
                
                return name.endsWith(extension);
            }
        });
        for(File f : matchingFiles) {
            files.add(f);
        }
        result[0] = files.size();
    }
    
    /**
     * Finds byte row in given file and starts method to change that row.
     * @param file File to look into
     * @param find Row of bytes that are searched 
     * @param change Row of bytes that will added
     * @param createCopy If it should creates copy of file
     * @throws IOException 
     */
    private void findBytes(File file, String find, String change, boolean createCopy) throws IOException {
        Path path = Paths.get(file.getPath());
        String output = file.getPath();
        byte[] table = Files.readAllBytes(path);
        for(byte b : table) System.out.println(b);
        Vector<Integer> vecFind = divide(find);
        Vector<Integer> vecChange = divide(change);

        
         for(int i = 0; i < table.length; i++) {
            for(int j = 0, k = i; j < vecFind.size(); k++, j++){
                if(k > table.length) 
                    break;
                
                if(vecFind.get(j) - 128 == table[k]){
                    if( j == ( vecFind.size() - 1) ){
                       table = changeBytes(table, vecChange, i, vecFind.size());
                       i += vecChange.size() + 1;
                       result[1]++;
                    }
                }
                
                else 
                    break;
            }
        }
         
        if(createCopy)
            output = file.getPath().substring(0, file.getPath().indexOf('.')) + "Copy" + file.getPath().substring(file.getPath().indexOf('.'));
        FileOutputStream fos = new FileOutputStream(output);
        fos.write(table);
        fos.close();
    }
    
    /**
     * Changes given section to bytes in vector.
     * @param table Table to change
     * @param to Row of bytes to change to
     * @param startIndex Where start deleting
     * @param lenghtOfFoundedRow Where end deleting
     * @return
     */
    private byte[] changeBytes(byte[] table, Vector<Integer> to, int startIndex, int lenghtOfFoundedRow){
        Vector<Integer> tableVec = new Vector<Integer>();
        for(byte b : table)
            tableVec.add((int) b);
        
        for(int x = 0; x < lenghtOfFoundedRow; x++){
            tableVec.remove(startIndex);
        }
       
        for(int x = 0; x < to.size(); x++)
            tableVec.add(startIndex + x, to.get(x));
        
        byte[] outTable = new byte[tableVec.size()];
        for(int x = 0; x < tableVec.size(); x++){
            outTable[x] = (byte) (tableVec.get(x) + 128);
        }
                     
        return outTable;
    }

    /**
     * Changes from hex to dec
     * @param s number in hex
     * @return value in dec
     */
    private int hexToDec(String s) {
         String digits = "0123456789ABCDEF";
         s = s.toUpperCase();
         int val = 0;
         for (int i = 0; i < s.length(); i++) {
             char c = s.charAt(i);
             int d = digits.indexOf(c);
             val = 16 * val + d;
         }
         return val;
     }
    
    /**
     * Divides string of hex numbers into Vector<Integer>
     * @param s Bytes in hex
     * @return Vector<Integer> with numbers from string
     */
    private Vector<Integer> divide(String s){
        Vector<Integer> vector = new Vector<Integer>();
        Vector<String> temp = new Vector<String>();
        for(int i = 0; i < s.length(); i += 2){
            vector.add(hexToDec(s.substring(i, i + 2)));
            temp.add(s.substring(i, i + 2) + "");
        }
        return vector;
    }
    
    private void checkDirectory() throws IOException{
        for(File f : dirs){
            if(f.getPath().contains("system32")){
                throw new IOException("Program nie pozwala na zmiany w folerze system32");
            }
                
        }
    }
}
