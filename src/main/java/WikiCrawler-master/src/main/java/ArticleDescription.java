public class ArticleDescription {
    public String name;
    public String id;
    public String category;
    public int sizeSymbol;
    public int level;
    public String addrURL;
    public String filePath;
    public int localNumber;
    public int totalNumber;
    public String parentId;

    public ArticleDescription(){
        this.name =  new String();
        this.id =  new String();
        this.parentId =  new String();
        this.category =  new String();
        this.sizeSymbol = 0;
        this.level = 0;
        this.addrURL = new String();
        this.filePath = new String();
        this.localNumber = 0;

    }

   public void copy(ArticleDescription source){
       this.name =  new String(source.name);
       this.category =  new String(source.category);
       this.sizeSymbol = source.sizeSymbol;
       this.level = source.level;
       this.addrURL = new String(source.addrURL);
       this.filePath = new String(source.filePath);
       this.localNumber = source.localNumber;
       this.parentId =  new String(source.parentId);
       this.id =  new String(source.id);
   }
}
