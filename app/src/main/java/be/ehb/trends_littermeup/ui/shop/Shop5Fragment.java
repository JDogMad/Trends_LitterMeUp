package be.ehb.trends_littermeup.ui.shop;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import be.ehb.trends_littermeup.Database;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;
import be.ehb.trends_littermeup.databinding.FragmentShopValue5Binding;
import be.ehb.trends_littermeup.ui.profile.ProfileFragment;

public class Shop5Fragment extends Fragment {
    private FragmentShopValue5Binding binding;
    Database db = new Database();
    Button btn_back, btn_redeem;
    String email, username;
    int points;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopValue5Binding.inflate(inflater, container, false);
        View root = binding.getRoot();




        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Database database = new Database();
        database.getUserFromDbByUid(mAuth.getUid()).observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                username = user.getUsername();
                email = user.getEmail();
                points = user.getPoints();
            }
        });

        btn_back = root.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.const_shop5, new ProfileFragment());

                Button button1 = getView().findViewById(R.id.btn_redeem_5);
                Button button2 = getView().findViewById(R.id.btn_back);

                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);

                fragmentTransaction.commit();
            }
        });

        btn_redeem = root.findViewById(R.id.btn_redeem_5);
        btn_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(points >= 500) {
                    // Generate a unique barcode for the coupon
                    String barcode = "123456";
                    int width = 400;
                    int height = 100;

                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    Bitmap barcodeBitmap = null;
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(barcode, BarcodeFormat.CODE_128, width, height);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        barcodeBitmap = barcodeEncoder.createBitmap(bitMatrix);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                    // Save the barcodeBitmap to a file
                    File barcodeFile = new File(getContext().getFilesDir(), "barcode.png");
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(barcodeFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    barcodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Create a Uri for the barcode file
                    Uri barcodeUri = FileProvider.getUriForFile(getContext(), "be.ehb.littermeup.fileprovider", barcodeFile);

                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
                    props.put("mail.smtp.port", "587"); //TLS Port
                    props.put("mail.smtp.auth", "true"); //enable authentication
                    props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

                    Session session = Session.getDefaultInstance(props,
                            new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication("help.littermeup@gmail.com", "wehwpibgmbimldto");
                                }
                            });

                    try {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("help.littermeup@gmail.com"));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                        message.setSubject("Coupon Redemption");

                        // Create a MimeBodyPart for the message text
                        MimeBodyPart messageBodyPart = new MimeBodyPart();
                        messageBodyPart.setText("Hi " + username + "\n\nYour 5 euro's coupon has been redeemed. Please show the following barcode at the store:\n\n");

                        // attach the barcode image
                        MimeBodyPart imagePart = new MimeBodyPart();
                        imagePart.setHeader("Content-ID", "<barcode>");
                        imagePart.setDisposition(MimeBodyPart.INLINE);
                        imagePart.attachFile(barcodeFile);

                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(messageBodyPart);
                        multipart.addBodyPart(imagePart);
                        message.setContent(multipart);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Transport.send(message);
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        points = points - 500;
                        db.getUserFromDbByUid(mAuth.getUid()).observe(getViewLifecycleOwner(), new Observer<User>() {
                            @Override
                            public void onChanged(User user) {
                                db.changePointsOnUser(points, user);
                            }
                        });
                        System.out.println("Email sent");

                    } catch (MessagingException | IOException e) {
                        throw new RuntimeException(e);
                    }


                    TextView couponStatusTextView = root.findViewById(R.id.coupon_succes);
                    String couponRedeemedText = "Your coupon has been redeemed. Be sure to check your mail, if anything went wrong please contact us at help.littermeup@gmail.com";
                    couponStatusTextView.setText(couponRedeemedText);

                    // create a CountDownTimer that counts down for 5 seconds
                    CountDownTimer timer = new CountDownTimer(5000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            // do nothing, just let the timer count down
                        }
                        public void onFinish() {
                            // Hide textview
                            couponStatusTextView.setVisibility(View.GONE);
                        }
                    };

                    // start the timer
                    timer.start();
                } else {
                    Toast.makeText(getContext(), "You do not have enough points!", Toast.LENGTH_LONG).show();
                }

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
