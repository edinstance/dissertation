import ShopItem from "@/components/Items/ShopItem";

export default async function ItemPage({
  params,
}: {
  params: { itemId: string };
}) {
  const { itemId } = await params;

  const stripeKey = process.env.STRIPE_PUBLISHABLE_KEY;

  if (!stripeKey) {
    throw new Error("Stripe publishable key is not defined");
  }

  return <ShopItem itemId={itemId} stripeKey={stripeKey} />;
}
