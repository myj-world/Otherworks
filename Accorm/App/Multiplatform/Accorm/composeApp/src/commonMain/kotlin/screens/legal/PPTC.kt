package screens.legal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.MailBulk
import compose.icons.fontawesomeicons.solid.ShieldAlt
import screens.assets.Contact
import screens.assets.CopyrightMessage
import screens.poppins
import viewmodels.CurrentEmailName

object PPTC : Tab {
    private fun readResolve(): Any = PPTC
    override val options: TabOptions
        @Composable
        get() {
            val title = "PPTC"
            val icon = rememberVectorPainter(FontAwesomeIcons.Solid.ShieldAlt)
            return remember {
                TabOptions(
                    index = 4u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        @Composable
        fun subHeading(subheading: String) {
            Text(
                text = subheading,
                fontSize = 28.sp,
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold
            )
        }

        @Composable
        fun body(content: String) {
            Text(
                text = content,
                fontSize = 18.sp,
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(30.dp))
        }

        Column (
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(31, 31, 54)
                )
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Privacy \n\n& Terms",
                fontSize = 48.sp,
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            )
            body("Last Updated: 24/09/24")
            Spacer(Modifier.height(20.dp))
            body("Accorm is a sub-service of Ginastic, and all the Ginastic Terms & Conditions, and Privacy Policies apply to Accorm")
            subHeading("Introduction")
            body("In some cases, we collect your information. We don't harm your data, modify, or use it for sharing it to others or third-party, nor we use any other tools that may use third-party services. However, we only use a third-party service to measure the traffic on our website and we don't intend to steal or use your personal data from the service. Our policy is subject to change anytime and you also agree to this change while you are using our services. Our goal is not only to provide a platform for showing-your-talent and provide wide range of tools, but also to create a platform where the pictures of animals, humans, and personfication are not involved. We are further improving our services by taking further security measures and enhancements.")
            subHeading("Rights & Responsibilities")
            body("By using Ginastic and its services, users agree: \n- NOT to spam/scam or submit abusive, unethical, or illegal content\n- NOT to submit any malicious or virus-containing files or links\n- NOT to copy, share, resell or re-use any content or code of the website thereof.\n- NOT to use any premium content without paying for it, or using without having explicit permission for premium content usage\n The users hold the following rights:\n- to claim the authority over the unrightful use of their content, providing proof of authority (or misuse)\n- to request removal of their content, provided they are not breaking the terms of an agreement and/or a contract")
            subHeading("Data Collection")
            body("We only collect user's essential and basic data. We do not store any confidential information of the user. However, we use third-party application to collect website traffic and website engagement data only. Many security measures have been taken into account to ensure smooth and safe usability and to protect leakage of data from various attacks. Some of the features of Ginastic make use of cookies and sessions to collect and store individual user's preferences and website interaction data, not any personal or sensitive data. Disabling of cookies and/or sessions might cause the website to not function as a whole.")
            subHeading("Changes & Updates")
            body("Being a user of this website, you agree thereof to comply with any changes to the policies. We will update the policies based on new security issues and basing on any new feature developed on the website. Therefore, we do not intend to keep changing the policy on set intervals, but rather based on progress and development.")
            subHeading("Content Credits Policy")
            body("A major policy to consider is to give credits to the rightful author of the resource(s) properly. However, there are some websites that we do not keep any content from without approval(such as SaveMyExams), due to certain reasons such as disallowance for re-using their content elsewhere. All the content we use here is either free for the public, or we have taken permission from the legal authors. If the ultimate author finds their content being used without their permission or have any other issues, they can directly contact us through contact@ginastic.co.")
            subHeading("Ginastic Home")
            body("On Ginastic Home, we have some conditions which may result in account suspension or even deletion in severe cases.\n- If username or user description includes bad words, abusive, or contains special characters symbolizing bad words may lead to account deletion.\n- One account per user is recommended.\n- Your name will be displayed with your products or collaborations, for example blog posts, apps, etc.\n- User email cannot be changed once an account is successfully created.")
            subHeading("Ginastic Feedback")
            body("The main purpose of the feedback form is to get advice and review on the website's performance, features, etc. It is ok if the form is being used for advertising products which can help Ginastic. However, only a few chosen feedbacks are shown publicly.")
            subHeading("Contact Credits and Author Rights")
            body("All the content uploaded on Ginastic is not intended to spread rumors, discuss any political affairs, provide false information, or promote hatred for anyone or any association. The content uploaded on Ginastic does not intend to violate the intellectual property of the ultimate authors' work which is uploaded or allow any plagiarism. We do not earn any income via any free content used from third-parties. We thoroughly review any terms & conditions which are mentioned by the author of resource and act accordingly. Some major websites from which we do not allow any content from are SaveMyExams, Physics&MathsTutor and Znotes official website.\n" +
                    "\n" +
                    "We do not intend to plagiarise any content or violate intellectual property. However, if any content, or resource, is not properly credited to the legal author, the legal author has the right to claim authority over their resource and request removal or editing, provided that they put forward any sort of proof to claim their ownership, which may include e.g. sending email via the official contact email for queries on the specific content. The email button below may be used for submitting a request:")
            val navigator = LocalNavigator.currentOrThrow
            Button(
                onClick = {
                    CurrentEmailName.setEmail("contact@accorm.ginastic.co")
                    CurrentEmailName.setName("Us")
                    navigator.push(Contact)
                },
                modifier = Modifier
                    .height(50.dp)
                    .clip(RoundedCornerShape(100)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(144, 144, 214)
                )
            ) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.MailBulk,
                    contentDescription = "Email",
                    tint = Color.White,
                    modifier = Modifier.height(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Email",
                    color = Color.White,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(30.dp))
            CopyrightMessage()
            Spacer(Modifier.height(70.dp))
        }
    }
}