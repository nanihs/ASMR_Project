import com.sun.deploy.security.SelectableSecurityManager;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
import static java.lang.Thread.sleep;


public class Music {
    private static int scale;//個体数
    private static int Asmrnum;//遺伝子座数
    private int num;
    private int MAX_GEN;//世代数
    private  int[][] oldpop;
    private int[][] newpop;
    private int[][] selectpop;
    private int[] fitness;
    private float avfFit;
    private int maxFit;
    private static int t;


    private float[] Pi;
    private float Pc;
    private float Pm;

    private Random random;

    public static void main(String[] args) throws FileNotFoundException, JavaLayerException, InterruptedException {
        System.out.println("---Let's Start..!!---");
        System.out.println(" ");
        Music music = new Music(8, 6, 10, 0.9f, 0.05f);
        music.init();
        music.initGroup();
        music.solve();
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("🌹🌹The experiment is over! Thank you for your help!!!🌹🌹");

    }

    public Music() {
    }

    public Music(int scale, int asmrnum, int MAX_GEN, float pc, float pm) {
        this.scale = scale;
        Asmrnum = asmrnum;
        this.MAX_GEN = MAX_GEN;
        Pc = pc;
        Pm = pm;
    }

    private void init(){
        oldpop = new int[scale][Asmrnum];
        newpop = new int[scale][Asmrnum];
        selectpop = new int[scale][Asmrnum];
        t = 0;
        fitness = new int[scale];
        Pi = new float[scale];
        random = new Random(System.currentTimeMillis());
        num = 19;
        avfFit = 0f;
        maxFit = 0;
    }

    void initGroup(){
        int i, j, k;
        for (k = 0; k < scale; k++) {
            oldpop[k][0] = random.nextInt(65535) % (num-1)+1;
            //oldpop[k][0] =0;
            for (i = 1; i < Asmrnum;) {
                oldpop[k][i] = random.nextInt(65535) % num;
                if(oldpop[k][i] != 0){
                    for (j = 0; j < i; j++) {
                        if(oldpop[k][i] == oldpop[k][j]){
                            break;
                        }
                    }
                    if(j == i){
                        i++;
                    }else {
                        i++;
                    }
                }
                // oldpop[k][i-1] =0;

            }
            oldpop[k][random.nextInt(65535) % Asmrnum]=0;
        }
    }

    public void countRate(){
        int k;
        double sumFitness = 0;
        for (k = 0; k < scale; k++) {
            sumFitness += fitness[k];
        }

        Pi[0] = (float)(fitness[0] / sumFitness);
        for (k = 1; k < scale; k++) {
            Pi[k] = (float)(fitness[k] / sumFitness + Pi[k - 1]);
        }
    }


    public void setFitness(int[][] chromosome) throws FileNotFoundException, JavaLayerException, InterruptedException {
        int flag = 0;
        //boolean flag = false; //230110南さんのアドバイスで改変
        Scanner scan = new Scanner(System.in);
        String input;
        System.out.println("<<<This is No." + t + " generation>>>");
        for (int i = 0; i < scale; i++) {
            System.out.println("---This is No." + (i + 1) + " ASMR---");
            play(chromosome[i]);
            sleep(11000);
            System.out.println("---please input your fitness (1 ~ 7)---");
            System.out.println("if you want to listen to it again, please input '0'!");
            while (true){
                input = scan.next();
                try {
                    while(Integer.parseInt(input) > 7){
                        System.out.println("*****************************************************");
                        System.out.println("*******please input your fitness again!(1 ~ 7)*******");
                        System.out.println("***if you want to listen to it again, please input '0'!***");
                        System.out.println("*****************************************************");
                        input = scan.next();
                    }
                    while(Integer.parseInt(input) == 0){
                        System.out.println("---This is No." + (i + 1) + " ASMR---");
                        play(chromosome[i]);
                        sleep(10500);
                        System.out.println("---please input your fitness (1 ~ 7)---");
                        System.out.println("if you want to listen to it again, please input '0'!");
                        input = scan.next();
                    }
                    break;
                }catch (NumberFormatException ne){
                    System.out.println("*****************************************************");
                    System.out.println("*******please input your fitness again!(1 ~ 7)*******");
                    System.out.println("***if you want to listen to it again, please input '0'!***");
                    System.out.println("*****************************************************");
                }
            }
            fitness[i] = Integer.parseInt(input);
        }
    }

    public void play(int[] chromosome) throws FileNotFoundException, JavaLayerException {

        File music1 = new File("/Users/krazdoll/IdeaProjects/ASMR_New/sucai_New_1/" + chromosome[0] +".mp3");
        File music2 = new File("/Users/krazdoll/IdeaProjects/ASMR_New/sucai_New_2/" + chromosome[1] +".mp3");
        File music3 = new File("/Users/krazdoll/IdeaProjects/ASMR_New/sucai_New_3/" + chromosome[2] +".mp3");
        File music4 = new File("/Users/krazdoll/IdeaProjects/ASMR_New/sucai_New_4/" + chromosome[3] +".mp3");
        File music5 = new File("/Users/krazdoll/IdeaProjects/ASMR_New/sucai_New_5/" + chromosome[4] +".mp3");
        File music6 = new File("/Users/krazdoll/IdeaProjects/ASMR_New/sucai_New_6/" + chromosome[5] +".mp3");

        Player player1 = new Player(new FileInputStream(music1));
        Player player2 = new Player(new FileInputStream(music2));
        Player player3 = new Player(new FileInputStream(music3));
        Player player4 = new Player(new FileInputStream(music4));
        Player player5 = new Player(new FileInputStream(music5));
        Player player6 = new Player(new FileInputStream(music6));
        new Thread(
                ()->{
                    try {
                        player1.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
        new Thread(
                ()->{
                    try {
                        player2.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
        new Thread(
                ()->{
                    try {
                        player3.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
        new Thread(
                ()->{
                    try {
                        player4.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
        new Thread(
                ()->{
                    try {
                        player5.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
        new Thread(
                ()->{
                    try {
                        player6.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
        ).start();

    }

    public void evolution(){
        int k;
        float r;
        select();
        for (k = 0; k < scale; k += 2) {
            r = random.nextFloat();
            if(r < Pc){
                SPCross(k, k+1);
            }
        }
        mutation();
        for (int i = 0; i < scale; i++) {
            for (int j = 0; j < Asmrnum; j++) {
                newpop[i][j] = selectpop[i][j];
            }
        }
        selectBestGh();
    }

    public void mutation(){
        float ran1;
        int ran2,sum;
        for (int i = 0; i < scale; i++) {
            //for (int j = 0; j < Asmrnum; j++) {
            //  System.out.println(selectpop[i][j] );
            //}
            for (int j = 0; j < Asmrnum; j++) {
                ran1 = random.nextFloat();
                ran2 = random.nextInt(65535) % num;
                for (int k = 0; k < Asmrnum; k++) {
                    while (selectpop[i][k] == ran2) {
                        ran2 = random.nextInt(65535) % num;
                    }
                }
                if (ran1 < Pm) {
                    selectpop[i][j] = ran2;
                }
            }
            sum=0;
            for (int j = 0; j < Asmrnum; j++) {
                //System.out.println(selectpop[i][j] );
                sum+=selectpop[i][j];
            }
            //System.out.println("和"+sum);
            if(sum==0) i--;
        }
    }

    void OXCross1(int k1, int k2) {
        int i, j, k, flag;
        int ran1, ran2, temp;
        int[] Gh1 = new int[Asmrnum];
        int[] Gh2 = new int[Asmrnum];

        ran1 = random.nextInt(65535) % Asmrnum;
        ran2 = random.nextInt(65535) % Asmrnum;

        while (ran1 == ran2) {
            ran2 = random.nextInt(65535) % Asmrnum;
        }

        if (ran1 > ran2)
        {
            temp = ran1;
            ran1 = ran2;
            ran2 = temp;
        }
        flag = ran2 - ran1 + 1;
        for (i = 0, j = ran1; i < flag; i++, j++) {
            Gh1[i] = selectpop[k2][j];
            Gh2[i] = selectpop[k1][j];
        }

        for (k = 0, j = flag; j < Asmrnum;)
        {
            Gh1[j] = selectpop[k1][k++];
            for (i = 0; i < flag; i++) {
                if (Gh1[i] == Gh1[j]) {
                    break;
                }
            }
            if (i == flag) {
                j++;
            }
        }

        for (k = 0, j = flag; j < Asmrnum;)
        {
            Gh2[j] = selectpop[k2][k++];
            for (i = 0; i < flag; i++) {
                if (Gh2[i] == Gh2[j]) {
                    break;
                }
            }
            if (i == flag) {
                j++;
            }
        }
        for (i = 0; i < Asmrnum; i++) {
            selectpop[k1][i] = Gh1[i];
            selectpop[k2][i] = Gh2[i];
        }
    }
    private void OxCross(int k1, int k2) {
        int ran1, ran2, temp;
        int i, j, flag, k;
        int[] Gh1 = new int[Asmrnum];
        int[] Gh2 = new int[Asmrnum];

        ran1 = random.nextInt(65535) % Asmrnum;
        ran2 = random.nextInt(65535) % Asmrnum;
        while(ran1 == ran2){
            ran2 = random.nextInt(65535) % Asmrnum;
        }
        if(ran1 > ran2){
            temp = ran1;
            ran1 = ran2;
            ran2 = temp;
        }
        for (i = 0, j = ran2; j < Asmrnum; i++, j++) {
            Gh2[i] = selectpop[k1][j];
        }
        flag = i;
        for (k = 0, j = flag; j < Asmrnum;) {
            if(k == 5){
                break;
            }
            Gh2[j] = selectpop[k2][k++];
            for(i = 0; i < flag; i++){
                if(Gh2[i] == Gh2[j]){
                    break;
                }
            }
            if(i == flag){
                j++;
            }
        }
        flag = ran1;
        for (k = 0, j = 0; k < Asmrnum;)
        {
            Gh1[j] = selectpop[k1][k++];
            for (i = 0; i < flag; i++) {
                if (selectpop[k2][i] == Gh1[j]) {
                    break;
                }
            }
            if (i == flag) {
                j++;
            }
        }
        flag = Asmrnum - ran1;
        for (i = 0, j = flag; j < Asmrnum; j++, i++) {
            Gh1[j] = selectpop[k2][i];
        }

        for (i = 0; i < Asmrnum; i++) {
            selectpop[k1][i] = Gh1[i];
            selectpop[k2][i] = Gh2[i];
        }
    }

    private void SPCross(int k1, int k2){
        int i, j, k;
        boolean flag = true;
        int ran;
        int[] Gh1 = new int[Asmrnum];
        int[] Gh2 = new int[Asmrnum];

        ran = random.nextInt(65535) % Asmrnum;
        for (i = 0; i < ran; i++) {
            Gh1[i] = selectpop[k1][i];
            Gh2[i] = selectpop[k2][i];
        }
        for (j = ran; j < Asmrnum; j++){
            Gh1[j] = selectpop[k2][j];
            Gh2[j] = selectpop[k1][j];
        }

        for (k = 0; k < Asmrnum; k++) {
            selectpop[k1][k] = Gh1[k];
            selectpop[k2][k] = Gh2[k];
        }

    }

    public void selectBestGh(){
        int maxId = 0;
        int pos;
        int maxEvaluation = fitness[0];
        for (int k = 1; k < scale; k++) {
            if(maxEvaluation < fitness[k]){
                maxEvaluation = fitness[k];
                maxId = k;
            }
        }

        pos = random.nextInt(65565) % scale;
        copyGh(pos, maxId);
    }

    private void OnCvariation(int k) {
        int ran1, ran2, temp;
        int count;
        count = random.nextInt(65535) % Asmrnum;
        for (int i = 0; i < count; i++) {
            ran1 = random.nextInt(65535) % Asmrnum;
            ran2 = random.nextInt(65535) % Asmrnum;
            while(ran1 == ran2){
                ran2 = random.nextInt(65535) % Asmrnum;
            }
            temp = selectpop[k][ran1];
            selectpop[k][ran1] = selectpop[k][ran2];
            selectpop[k][ran2] = temp;
        }
    }

    public void copyGh(int k, int kk){
        for (int i = 0; i < Asmrnum; i++) {
            newpop[k][i] = oldpop[kk][i];
        }
    }

    public void selectCopyGh(int k, int kk){
        for (int i = 0; i < Asmrnum; i++) {
            selectpop[k][i] = oldpop[kk][i];
        }
    }

    public void printFitness(){
        int maxEvaluation = fitness[0];
        for (int k = 1; k < scale; k++) {
            if(maxEvaluation < fitness[k]){
                maxEvaluation = fitness[k];
            }

        }
        System.out.println("第" + t + "世代のmaxfitness：" + maxEvaluation);
        for (int j = 0; j < scale; j++) {
            for (int l = 0; l < Asmrnum; l++) {
                System.out.print(oldpop[j][l] + "  ");
            }
            System.out.println("   fitness:" + fitness[j]);
        }
        System.out.println(" ");
        System.out.println("========================================================================");
        System.out.println(" ");
    }

    public void select(){
        int selectId1, selectId2, i, j;
        float ran1, ran2;
        for (int k = 0; k < scale; k ++) {
            ran1 = (float)(random.nextInt(65535) % 1000 / 1000.0);
            for (i = 0; i < scale; i++) {
                if(ran1 <= Pi[i]){
                    break;
                }
            }
//            ran2 = (float)(random.nextInt(65535) % 1000 / 1000.0);
//            for (j = 0; j < scale; j++) {
//                if(ran2 <= Pi[j]){
//                    break;
//                }
//            }
//            while(i == j){
//                ran2 = (float)(random.nextInt(65535) % 1000 / 1000.0);
//                for (j = 0; j < scale; j++) {
//                    if(ran2 <= Pi[j]){
//                        break;
//                    }
//                }
//            }
            selectId1 = i;
//            selectId2 = j;
            selectCopyGh(k, selectId1);
//            selectCopyGh(k + 1, selectId2);
        }
    }

    public void avgFitness(){

        int sum = 0;
        for (int i = 0; i < scale; i++) {
            sum += fitness[i];
        }
        avfFit = (float)sum / scale;
    }

    public void maxFitness(){
        int maxEvaluation = fitness[0];
        for (int k = 1; k < scale; k++) {
            if(maxEvaluation < fitness[k]){
                maxEvaluation = fitness[k];
            }
        }
        maxFit = maxEvaluation;
    }

    public static void exportFitness(int[][] chromosome, int[] fitness, float avfFit, int maxFit){
        //File writeFile = new File("C:\\ASMR_New\\result.csv");
        File writeFile = new File("/Users/krazdoll/IdeaProjects/ASMR_New/result.csv");
        try{
            BufferedWriter writeText = new BufferedWriter(new FileWriter(writeFile, true));
            writeText.write("Generation:" + t);
            writeText.newLine();
            for (int i = 0; i < scale; i++) {

                writeText.write(i + ",");
                for (int j = 0; j < Asmrnum; j++) {
                    writeText.write(chromosome[i][j] + ",");
                }
                writeText.write(fitness[i] + ",");
                writeText.newLine();
            }
            writeText.write("avgfit:" + "," + avfFit + "," + "maxfit" + "," + maxFit);
            writeText.newLine();
            writeText.newLine();
            writeText.close();
        } catch (IOException e) {
            System.out.println("error!");
        }
    }

    public void solve() throws FileNotFoundException, JavaLayerException, InterruptedException {
        int i, k;
        initGroup();
        setFitness(oldpop);
        avgFitness();
        maxFitness();
        exportFitness(oldpop, fitness, avfFit, maxFit);
        printFitness();
        countRate();
        for (t = 1; t < MAX_GEN; t++){
            evolution();
            for (k = 0; k < scale; k++){
                for (i = 0; i < Asmrnum; i++) {
                    oldpop[k][i] = newpop[k][i];
                }
            }
            setFitness(oldpop);
            avgFitness();
            maxFitness();
            exportFitness(oldpop, fitness, avfFit, maxFit);
            printFitness();
            countRate();
        }
    }
}